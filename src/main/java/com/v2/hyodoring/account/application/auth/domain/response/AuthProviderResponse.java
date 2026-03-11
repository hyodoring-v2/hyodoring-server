package com.v2.hyodoring.account.application.auth.domain.response;

import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.core.shared.domain.Env;


public record AuthProviderResponse(
        Env env,
        Provider provider
) {
}
