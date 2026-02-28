package com.v2.hyodoring.account.core.auth.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class AccountAuth {
    private final Long id;
    private final Long accountId;
    private final Long authId;
    private final AuthType authType;

    private AccountAuth(
            Long id,
            Long accountId,
            Long authId,
            AuthType authType
    ) {
        Assert.notNull(id, "id must not be null");
        Assert.notNull(accountId, "accountId must not be null");
        Assert.notNull(authId, "authId must not be null");
        Assert.notNull(authType, "authType must not be null");
        this.id = id;
        this.accountId = accountId;
        this.authId = authId;
        this.authType = authType;
    }

    public static AccountAuth of(
            Long id,
            Long accountId,
            Long authId,
            AuthType authType
    ) {
        return new AccountAuth(id, accountId, authId, authType);
    };

}
