package com.v2.hyodoring.account.application.auth.service.oidc;

import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.infrastructure.feign.auth.OIDCPayload;

public interface OIDCService {
    boolean supports(Provider provider);
    OIDCPayload verify(String idToken);
    OIDCPayload signIn(String idToken);
}
