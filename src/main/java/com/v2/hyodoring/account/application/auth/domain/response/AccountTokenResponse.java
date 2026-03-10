package com.v2.hyodoring.account.application.auth.domain.response;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

@Getter
public class AccountTokenResponse {
    private final Long accountId;
    private final List<Long> joinedFamilyIdList;
    private final String name;
    private final String accessToken;
    private final String refreshToken;

    private AccountTokenResponse(Long accountId, List<Long> joinedFamilyIdList, String name, String accessToken, String refreshToken) {
        Assert.notNull(accountId, "accountId can not be null");
        Assert.notEmpty(joinedFamilyIdList, "joinedFamilyIdList can not be empty");
        Assert.notNull(name, "name can not be null");
        Assert.notNull(accessToken, "accessToken can not be null");
        Assert.notNull(refreshToken, "refreshToken can not be null");
        this.accountId = accountId;
        this.joinedFamilyIdList = joinedFamilyIdList;
        this.name = name;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AccountTokenResponse of(Long accountId, List<Long> joinedFamilyIdList, String name, String accessToken, String refreshToken) {
        return new AccountTokenResponse(accountId, joinedFamilyIdList, name, accessToken, refreshToken);
    }
}
