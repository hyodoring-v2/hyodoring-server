package com.v2.hyodoring.family.application.family.domain.response;

import com.v2.hyodoring.family.core.family.Level;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
public class FamilyMemberResponse {
    private final Long accountId;
    private final String nickname;
    private final Level level;
    private final FamilyRoleType role;
    private final LocalDateTime joinedAt;

    private FamilyMemberResponse(Long accountId, String nickname, Level level, FamilyRoleType role, LocalDateTime joinedAt) {
        Assert.notNull(accountId, "accountId must not be null.");
        Assert.hasText(nickname, "nickname must not be empty.");
        Assert.notNull(level, "level must not be null.");
        Assert.notNull(role, "role must not be null.");
        Assert.notNull(joinedAt, "joinedAt must not be null.");
        this.accountId = accountId;
        this.nickname = nickname;
        this.level = level;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public static FamilyMemberResponse of(Long accountId, String nickname, Level level,
                                          FamilyRoleType role, LocalDateTime joinedAt) {
        return new FamilyMemberResponse(accountId, nickname, level, role, joinedAt);
    }
}
