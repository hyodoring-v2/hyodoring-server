package com.v2.hyodoring.family.application.family;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public final class FamilyRoleData {
    private final Long familyId;
    private final String familyName;
    private final String familyCode;
    private final Long accountId;
    private final String role;

    private FamilyRoleData(Long familyId,
                           String familyName,
                           String familyCode,
                           Long accountId,
                           String role) {
        Assert.notNull(familyId, "familyId is null");
        Assert.hasText(familyName, "familyName is empty");
        Assert.hasText(familyCode, "familyCode is empty");
        Assert.notNull(accountId, "accountId is null");
        Assert.hasText(role, "role is empty");
        this.familyId = familyId;
        this.familyName = familyName;
        this.familyCode = familyCode;
        this.accountId = accountId;
        this.role = role;
    }

    public static FamilyRoleData of(Long familyId,
                                   String familyName,
                                   String familyCode,
                                   Long accountId,
                                   String role) {
        return new FamilyRoleData(familyId, familyName, familyCode, accountId, role);
    }
}
