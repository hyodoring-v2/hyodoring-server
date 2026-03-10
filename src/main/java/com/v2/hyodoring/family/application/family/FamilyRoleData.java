package com.v2.hyodoring.family.application.family;

import com.v2.hyodoring.family.core.family.Level;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public final class FamilyRoleData {
    private final Long familyId;
    private final String familyName;
    private final String familyCode;
    private final Level level;
    private final FamilyRoleType role;

    private FamilyRoleData(Long familyId,
                           String familyName,
                           String familyCode,
                           Level level,
                           FamilyRoleType role) {
        Assert.notNull(familyId, "familyId is null");
        Assert.hasText(familyName, "familyName is empty");
        Assert.hasText(familyCode, "familyCode is empty");
        Assert.notNull(level, "level is null");
        Assert.notNull(role, "role is empty");
        this.familyId = familyId;
        this.familyName = familyName;
        this.familyCode = familyCode;
        this.level = level;
        this.role = role;
    }

    public static FamilyRoleData of(Long familyId,
                                   String familyName,
                                   String familyCode,
                                   Level level,
                                   FamilyRoleType role) {
        return new FamilyRoleData(familyId, familyName, familyCode, level, role);
    }
}
