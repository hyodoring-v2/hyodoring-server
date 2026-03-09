package com.v2.hyodoring.account.application.auth.service.oidc;

import com.v2.hyodoring.account.application.auth.service.AuthQueryService;
import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.core.shared.provider.EnvProvider;
import com.v2.hyodoring.account.core.auth.domain.OIDCPublicKey;
import com.v2.hyodoring.account.infrastructure.feign.auth.kakao.KakaoFeignClient;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AuthProviderEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.OIDCPublicKeyEntity;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.AuthProviderQueryRepository;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.OIDCPublicKeyCommandRepository;
import com.v2.hyodoring.account.infrastructure.jpa.auth.repository.OIDCPublicKeyQueryRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KakaoOIDCService extends AbstractOIDCService {
    private final EnvProvider envProvider;
    private final AuthProviderQueryRepository authProviderQueryRepository;
    private final OIDCPublicKeyQueryRepository oidcPublicKeyQueryRepository;
    private final OIDCPublicKeyCommandRepository oidcPublicKeyCommandRepository;
    private final KakaoFeignClient kakaoFeignClient;

    public KakaoOIDCService(
            EnvProvider envProvider,
            AuthQueryService authQueryService,
            AuthProviderQueryRepository authProviderQueryRepository,
            OIDCPublicKeyQueryRepository oidcPublicKeyQueryRepository,
            OIDCPublicKeyCommandRepository oidcPublicKeyCommandRepository,
            @Qualifier("KakaoOIDCTokenVerifier") OIDCTokenVerifier oidcTokenVerifier,
            KakaoFeignClient kakaoFeignClient) {
        super(authQueryService, oidcTokenVerifier);
        this.envProvider = envProvider;
        this.authProviderQueryRepository = authProviderQueryRepository;
        this.oidcPublicKeyQueryRepository = oidcPublicKeyQueryRepository;
        this.oidcPublicKeyCommandRepository = oidcPublicKeyCommandRepository;
        this.kakaoFeignClient = kakaoFeignClient;
    }

    @Override
    public boolean supports(Provider provider) {
        return Provider.KAKAO.equals(provider);
    }

    @Override
    protected List<OIDCPublicKey> getPublicKeys() {
        // 현재 환경의 카카오 인증 제공자 ID 조회
        Long providerId = authProviderQueryRepository
                .findByNameAndEnv(Provider.KAKAO, envProvider.getActiveProfile())
                .orElseThrow(() -> new IllegalStateException("Kakao Auth Provider not found for environment: " + envProvider.getActiveProfile()))
                .getId();

        // DB에서 OIDC 공개 키 조회
        List<OIDCPublicKey> oidcPublicKeys = oidcPublicKeyQueryRepository.findByProviderId(providerId).stream()
                .map(OIDCPublicKeyEntity::toVo)
                .toList();

        // DB에 공개 키가 없는 경우 카카오 API에서 직접 조회
        if (oidcPublicKeys.isEmpty()) {
            List<OIDCPublicKey> publicKeys = kakaoFeignClient.getPublicKeys().getKeys();
            publicKeys.forEach(key ->
                    oidcPublicKeyCommandRepository.save(OIDCPublicKeyEntity.of(providerId, key)));
            return publicKeys;
        }
        return oidcPublicKeys;
    }

    @Override
    protected AuthProviderEntity getAuthProvider() {
        return authProviderQueryRepository
                .findByNameAndEnv(Provider.KAKAO, envProvider.getActiveProfile())
                .orElseThrow(() -> new IllegalStateException("Kakao Auth Provider not found for environment: " + envProvider.getActiveProfile()));
    }
}
