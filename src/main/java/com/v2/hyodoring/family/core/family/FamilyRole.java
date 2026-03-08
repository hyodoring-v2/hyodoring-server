package com.v2.hyodoring.family.core.family;

import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FamilyRole {
    private final Long id;
    private final Long accountId;
    private final Long familyId;
    private final FamilyRoleType name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private FamilyRole(Long id, Long accountId, Long familyId, FamilyRoleType name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.familyId = familyId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FamilyRole create(Long accountId, Long familyId, FamilyRoleType name) {
        return new FamilyRole(null, accountId, familyId, name, null, null);
    }

    public static FamilyRole of(Long id, Long accountId, Long familyId, FamilyRoleType name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new FamilyRole(id, accountId, familyId, name, createdAt, updatedAt);
    }
}
