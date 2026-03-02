package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.core.auth.domain.AccountOAuthData;
import com.v2.hyodoring.account.core.shared.provider.EnvProvider;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AccountOAuthEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AuthProviderEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AccountOAuthQueryRepository;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AuthProviderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthQueryService {
    private final EnvProvider envProvider;
    private final AccountOAuthQueryRepository accountOAuthQueryRepository;
    private final AuthProviderQueryRepository authProviderQueryRepository;

    public boolean isOAuthSubjectLinked(Long providerId, String subject) {
        final AccountOAuthEntity accountOAuth = accountOAuthQueryRepository
                .findByProviderIdAndSubject(providerId, subject).orElse(null);
        return accountOAuth != null;
    }

    /**
     * 계정에 연결된 모든 OAuth 인증 정보 조회
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
