package com.v2.hyodoring.account.core.auth.domain;

import jakarta.annotation.Nullable;
import lombok.Getter;

@Getter
public final class AccountOAuthData {
    private final Long accountId;
    private final AuthProvider authProvider;
    private final String clientId;
    private final String redirectUri;
    @Nullable
    private final String email;

    private AccountOAuthData(
            Long accountId,
            AuthProvider authProvider,
            String clientId,
            String redirectUri,
            @Nullable String email
    ) {
        this.accountId = accountId;
        this.authProvider = authProvider;
        this.clientId = clientId;
        this.redirectUri = redirectUri;
        this.email = email;
    }

    public static AccountOAuthData of(
            Long accountId,
            AuthProvider authProvider,
            String clientId,
            String redirectUri,
            @Nullable String email
    ) {
        return new AccountOAuthData(accountId, authProvider, clientId, redirectUri, email);
    }

}
