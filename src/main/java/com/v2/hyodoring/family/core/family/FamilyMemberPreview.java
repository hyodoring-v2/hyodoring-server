package com.v2.hyodoring.family.core.family;

import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class FamilyMemberPreview {
    private final Long accountId;
    private final String nickname;
    private final FamilyRoleType role;

    private FamilyMemberPreview(Long accountId, String nickname, FamilyRoleType role) {
        Assert.notNull(accountId, "accountId can not be null");
        Assert.hasText(nickname, "nickname can not be empty");
        Assert.notNull(role, "role can not be null");
        this.accountId = accountId;
        this.nickname = nickname;
        this.role = role;
    }

    public static FamilyMemberPreview of(Long accountId, String nickname, FamilyRoleType role) {
        return new FamilyMemberPreview(accountId, nickname, role);
    }
}
