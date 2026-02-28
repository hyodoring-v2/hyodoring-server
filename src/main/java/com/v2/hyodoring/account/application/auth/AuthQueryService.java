package com.v2.hyodoring.account.application.auth;

import com.v2.hyodoring.account.core.auth.domain.AccountOAuthData;
import com.v2.hyodoring.account.core.shared.provider.EnvProvider;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AuthProviderEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AccountOAuthQueryRepository;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AuthProviderQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthQueryService {
    private final EnvProvider envProvider;
    private final AccountOAuthQueryRepository accountOAuthQueryRepository;
    private final AuthProviderQueryRepository authProviderQueryRepository;

    /**
     * 계정에 연결된 모든 OAuth 인증 정보 조회
     * @param accountId 계정 ID
     * @return 계정에 연결된 OAuth 인증 정보(provider, clientId, redirectUri, email) 목록 반환
     */
    public List<AccountOAuthData> getAllAccountOAuthData(Long accountId) {
        return accountOAuthQueryRepository.findAllByAccountId(accountId).stream()
                .map(accountOAuthEntity -> {
                    AuthProviderEntity entity = authProviderQueryRepository
                            .findByIdAndEnv(accountOAuthEntity.getProviderId(), envProvider.getActiveProfile())
                            .orElseThrow(() -> new IllegalArgumentException("Auth provider not found for providerId: " + accountOAuthEntity.getProviderId()));
                    return AccountOAuthData.of(
                            accountId,
                            entity.getName(),
                            entity.getClientId(),
                            entity.getRedirectUri(),
                            accountOAuthEntity.getEmail()
                    );
                })
                .toList();
    }
}
