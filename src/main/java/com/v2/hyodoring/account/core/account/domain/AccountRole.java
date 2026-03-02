package com.v2.hyodoring.account.core.account.domain;

import com.v2.hyodoring.account.core.role.AccountRoleType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountRole {
    private final Long id;
    private final Long accountId;
    private final AccountRoleType name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private AccountRole(Long id, Long accountId, AccountRoleType name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AccountRole create(Long accountId, AccountRoleType name) {
        return new AccountRole(null, accountId, name, null, null);
    }

    public static AccountRole of(Long id, Long accountId, AccountRoleType name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new AccountRole(id, accountId, name, createdAt, updatedAt);
    }
}
