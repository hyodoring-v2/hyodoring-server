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
    private final String nickname;
    private final Level level;
    private final LocalDateTime createdAt;
    private final Provider provider;
    @Nullable
    private final String email;
    private final List<FamilyRoleData> family;

    private AccountProfileResponse(
            String nickname,
            Level level,
            LocalDateTime createdAt,
            Provider provider,
            @Nullable String email,
            List<FamilyRoleData> family
    ) {
        Assert.hasText(nickname, "nickname must not be empty");
        Assert.notNull(level, "level must not be null");
        Assert.notNull(createdAt, "createdAt must not be null");
        Assert.notNull(provider, "authProvider must not be null");
        Assert.notNull(family, "family must not be null");
        this.nickname = nickname;
        this.level = level;
        this.createdAt = createdAt;
        this.provider = provider;
        this.email = email;
        this.family = family;
    }

    public static AccountProfileResponse of(
            String nickname,
            Level level,
            LocalDateTime createdAt,
            Provider provider,
            @Nullable String email,
            List<FamilyRoleData> family
    ) {
        return new AccountProfileResponse(nickname, level, createdAt, provider, email, family);
    }

}
