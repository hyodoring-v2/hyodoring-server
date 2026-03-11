package com.v2.hyodoring.account.core.auth.domain;

import com.v2.hyodoring.account.core.shared.domain.Env;

public record InternalAuthProvider(
        Env env,
        Provider provider,
        String clientId,
        String clientSecret,
        String redirectUri
) {
}