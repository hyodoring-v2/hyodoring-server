package com.v2.hyodoring.account.application.auth.service.oidc;

import com.v2.hyodoring.account.application.auth.domain.exception.AuthException;
import com.v2.hyodoring.account.application.auth.domain.exception.AuthErrorResponse;
import com.v2.hyodoring.account.application.auth.service.AuthQueryService;
import com.v2.hyodoring.account.core.auth.domain.Provider;
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
    public final OIDCPayload verify(String idToken) {
        return abstractOidcTokenVerifier.verify(idToken, getPublicKeys());
    }

    @Override
    public final OIDCPayload signIn(String idToken) {
        // authProvider 정보 조회
        final AuthProviderEntity authProvider = getAuthProvider();
        // payload 추출
        OIDCPayload payload = verify(idToken);
        // subject로 소셜 계정이 연동되지 않은 미가입 회원인 경우 404 반환
        if (!authQueryService.isOAuthSubjectLinked(authProvider.getId(), payload.getSubject())) {
            throw new AuthException(AuthErrorResponse.ACCOUNT_NOT_FOUND);
        }
        return payload;
    }

    public abstract boolean supports(Provider provider);

    protected abstract List<OIDCPublicKey> getPublicKeys();

    protected abstract AuthProviderEntity getAuthProvider();
}
