package com.v2.hyodoring.account.application.auth.domain.response;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class AccountTokenResponse {
    private final Long accountId;
    private final String name;
    private final String accessToken;
    private final String refreshToken;

    private AccountTokenResponse(Long accountId, String name, String accessToken, String refreshToken) {
        Assert.notNull(accountId, "accountId can not be null");
        Assert.notNull(name, "name can not be null");
        Assert.notNull(accessToken, "accessToken can not be null");
        Assert.notNull(refreshToken, "refreshToken can not be null");
        this.accountId = accountId;
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AccountTokenResponse of(Long accountId, String name, String accessToken, String refreshToken) {
        return new AccountTokenResponse(accountId, name, accessToken, refreshToken);
    }
}
