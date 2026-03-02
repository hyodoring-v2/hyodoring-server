package com.v2.hyodoring.account.application.auth.verifier;

import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.core.auth.domain.OIDCPublicKey;
import com.v2.hyodoring.account.infrastructure.feign.auth.OIDCPayload;

import java.util.List;

public interface OIDCTokenVerifier {
    boolean supports(AuthProvider provider);
    OIDCPublicKey getPublicKey(String idToken, List<OIDCPublicKey> publicKeys);
    OIDCPayload verify(String idToken, List<OIDCPublicKey> publicKeys);
}
