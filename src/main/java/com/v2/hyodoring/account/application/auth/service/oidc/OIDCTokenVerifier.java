package com.v2.hyodoring.account.application.auth.service.oidc;

import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.core.auth.domain.OIDCPublicKey;
import com.v2.hyodoring.account.infrastructure.feign.auth.OIDCPayload;

import java.util.List;

public interface OIDCTokenVerifier {
    boolean supports(Provider provider);
    OIDCPublicKey getPublicKey(String idToken, List<OIDCPublicKey> publicKeys);
    OIDCPayload verify(String idToken, List<OIDCPublicKey> publicKeys);
}
