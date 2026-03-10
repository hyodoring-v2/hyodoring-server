package com.v2.hyodoring.account.application.account.domain.response;

import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.family.application.family.FamilyRoleData;
import com.v2.hyodoring.family.core.family.Level;
import jakarta.annotation.Nullable;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class AccountProfileResponse {
    private final Long id;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final Provider provider;
    @Nullable
    private final String email;
    private final List<FamilyRoleData> family;

    private AccountProfileResponse(
            Long id,
            String nickname,
            LocalDateTime createdAt,
            Provider provider,
            @Nullable String email,
            List<FamilyRoleData> family
    ) {
        Assert.notNull(id, "id must not be null");
        Assert.hasText(nickname, "nickname must not be empty");
        Assert.notNull(createdAt, "createdAt must not be null");
        Assert.notNull(provider, "authProvider must not be null");
        Assert.notNull(family, "family must not be null");
        this.id = id;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.provider = provider;
        this.email = email;
        this.family = family;
    }

    public static AccountProfileResponse of(
            Long id,
            String nickname,
            LocalDateTime createdAt,
            Provider provider,
            @Nullable String email,
            List<FamilyRoleData> family
    ) {
        return new AccountProfileResponse(id, nickname, createdAt, provider, email, family);
    }

}
