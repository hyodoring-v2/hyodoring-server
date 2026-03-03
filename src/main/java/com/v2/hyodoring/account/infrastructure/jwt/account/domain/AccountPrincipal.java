package com.v2.hyodoring.account.infrastructure.jwt.account.domain;

import com.v2.hyodoring.account.core.role.AccountRoleType;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.Collections;

@Getter
public class AccountPrincipal implements UserDetails {
    private final long id;
    private final String nickname;
    private final AccountRoleType role;

    private AccountPrincipal(long id, String nickname, AccountRoleType role) {
        Assert.hasText(nickname, "nickname must not be empty");
        Assert.notNull(role, "role must not be null");
        this.id = id;
        this.nickname = nickname;
        this.role = role;
    }

    public static AccountPrincipal of(long id, String nickname, AccountRoleType role) {
        return new AccountPrincipal(id, nickname, role);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(() -> "ROLE_" + role.toString());
    }

    @Override
    public @Nullable String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return nickname;
    }
}
