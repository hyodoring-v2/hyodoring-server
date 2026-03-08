package com.v2.hyodoring.family.core.family;

import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
public class FamilyMember {
    private final Long accountId;
    private final String nickname;
    private final Integer score;
    private final FamilyRoleType role;
    private final LocalDateTime joinedAt;

    private FamilyMember(Long accountId, String nickname, Integer score, FamilyRoleType role, LocalDateTime joinedAt) {
        Assert.notNull(accountId, "accountId must not be null.");
        Assert.hasText(nickname, "nickname must not be empty.");
        Assert.notNull(score, "score must not be null.");
        Assert.notNull(role, "role must not be null.");
        Assert.notNull(joinedAt, "joinedAt must not be null.");
        this.accountId = accountId;
        this.nickname = nickname;
        this.score = score;
        this.role = role;
        this.joinedAt = joinedAt;
    }

    public static FamilyMember of(Long accountId, String nickname, Integer score,
                                  FamilyRoleType role, LocalDateTime joinedAt) {
        return new FamilyMember(accountId, nickname, score, role, joinedAt);
    }
}