package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.application.auth.verifier.OIDCTokenVerifier;
import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.core.auth.domain.OIDCPublicKey;
import com.v2.hyodoring.account.infrastructure.feign.auth.OIDCPayload;
import com.v2.hyodoring.account.infrastructure.jpa.auth.domain.AuthProviderEntity;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractOIDCService implements OIDCService {

    private final AuthQueryService authQueryService;
    private final OIDCTokenVerifier abstractOidcTokenVerifier;

    @Override
    public final void signIn(String idToken) {
        // authProvider 정보 조회
        final AuthProviderEntity authProvider = getAuthProvider();

        // id token 유효성 검증 및 payload 추출
        OIDCPayload payload = abstractOidcTokenVerifier.verify(idToken, getPublicKeys());

        // subject로 계정 연결 여부 확인
        if (!authQueryService.isOAuthSubjectLinked(authProvider.getId(), payload.getSubject())) {
            //TODO: 신규 회원가입
        } else {
            //TODO: 액세스 토큰 발급 및 로그인 처리
        }
    }

    public abstract boolean supports(AuthProvider provider);

    protected abstract List<OIDCPublicKey> getPublicKeys();

    protected abstract AuthProviderEntity getAuthProvider();
}
