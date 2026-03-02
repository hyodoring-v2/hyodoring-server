package com.v2.hyodoring.account.application.auth.verifier;

import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.infrastructure.feign.auth.OIDCPayload;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Component("KakaoOIDCTokenVerifier")
public final class KakaoOIDCTokenVerifier extends AbstractOIDCTokenVerifier {

    @Override
    public boolean supports(AuthProvider provider) {
        return AuthProvider.KAKAO.equals(provider);
    }

    @Override
    protected OIDCPayload verifyAndExtractPayload(String idToken, PublicKey publicKey) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(idToken)
                    .getPayload();
            return OIDCPayload.of(
                    claims.getSubject(),
                    claims.get("email", String.class),
                    claims.get("nickname", String.class)
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid ID Token", e);
        }
    }
}
