package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.application.account.service.AccountQueryService;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthException;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthErrorResponse;
import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.core.auth.domain.AccountOAuthData;
import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.core.shared.provider.EnvProvider;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AccountOAuthEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AuthProviderEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AccountOAuthQueryRepository;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AuthProviderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthQueryService {
    private final EnvProvider envProvider;
    private final AccountQueryService accountQueryService;
    private final AccountOAuthQueryRepository accountOAuthQueryRepository;
    private final AuthProviderQueryRepository authProviderQueryRepository;

    public boolean existsActiveAccountBySubject(Provider provider, String subject) {
        // Provider 조회
        final AuthProvider authProvider = getAuthProviderByName(provider);

        // 인증 정보 조회
        final AccountOAuthEntity accountOAuth = accountOAuthQueryRepository
                .findByProviderIdAndSubject(authProvider.getId(), subject).orElse(null);

        // 인증 정보가 null인 경우 false 반환
        if (accountOAuth == null) {
            return false;
        }
        return accountQueryService.getAccount(accountOAuth.getAccountId()).isActive();
    }

    /**
     * 인증 제공자 ID와 subject 값을 통해 OAuth 인증 정보를 조회하는 메서드
     * @param provider 인증 제공자
     * @param subject 플랫폼에서 발급한 사용자의 고유 식별값
     * @return {@link Account}
     */
    public Account getActiveAccountBySubject(Provider provider, String subject) {
        // Provider 조회
        final AuthProvider authProvider = getAuthProviderByName(provider);
        // 인증 정보 조회
        final AccountOAuthEntity accountOAuth = accountOAuthQueryRepository
                .findByProviderIdAndSubject(authProvider.getId(), subject)
                .orElseThrow(() -> new AuthException(AuthErrorResponse.ACCOUNT_NOT_FOUND));
        return accountQueryService.getActiveAccount(accountOAuth.getAccountId());
    }

    /**
     * 인증 제공자를 조회하는 메서드
     * @param provider {@link Provider}
     * @return {@link AuthProvider}
     */
    public AuthProvider getAuthProviderByName(Provider provider) {
        return authProviderQueryRepository
                .findByNameAndEnv(provider, envProvider.getActiveProfile())
                .orElseThrow(() -> new AuthException(AuthErrorResponse.UNREGISTERED_AUTH_PROVIDER))
                .toDomain();
    }

    /**
     * subject로 소셜 연동된 계정인지 확인하는 메서드
     * @param providerId 인증 제공자 ID
     * @param subject 플랫폼에서 발급한 사용자의 고유 식별값
     * @return 연동 여부 T/F
     */
    public boolean isOAuthSubjectLinked(Long providerId, String subject) {
        final AccountOAuthEntity accountOAuth = accountOAuthQueryRepository
                .findByProviderIdAndSubject(providerId, subject).orElse(null);
        return accountOAuth != null;
    }

    /**
     * 계정에 연결된 모든 OAuth 인증 정보를 조회하는 메서드
     * @param accountId 계정 ID
     * @return 계정에 연결된 OAuth 인증 정보(provider, clientId, redirectUri, email) 목록 반환
     */
    public List<AccountOAuthData> getAllAccountOAuthData(Long accountId) {
        final List<AccountOAuthEntity> oAuthEntities = accountOAuthQueryRepository.findAllByAccountId(accountId);

        if (oAuthEntities.isEmpty()) {
            throw new IllegalStateException("No OAuth data found for accountId: " + accountId);
        }

        final List<Long> providerIds = oAuthEntities.stream()
                .map(AccountOAuthEntity::getProviderId)
                .toList();

        // 현재 환경의 인증 제공자 정보 조회 및 매핑
        final Map<Long, AuthProviderEntity> providerMap = authProviderQueryRepository.findAllById(providerIds).stream()
                .filter(entity -> envProvider.getActiveProfile().equals(entity.getEnv()))
                .collect(Collectors.toMap(AuthProviderEntity::getId, entity -> entity));

        return oAuthEntities.stream()
                .filter(oAuthEntity -> providerMap.containsKey(oAuthEntity.getProviderId()))
                .map(oAuthEntity -> {
                    final AuthProviderEntity providerEntity = providerMap.get(oAuthEntity.getProviderId());
                    return AccountOAuthData.of(
                            accountId,
                            providerEntity.getName(),
                            providerEntity.getClientId(),
                            providerEntity.getRedirectUri(),
                            oAuthEntity.getEmail()
                    );
                })
                .toList();
    }
}
