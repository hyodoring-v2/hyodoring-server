package com.v2.hyodoring.family.core.family;

import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FamilyRole {
    private final Long id;
    private final Long familyId;
    private final FamilyRoleType name;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private FamilyRole(Long id, Long familyId, FamilyRoleType name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.familyId = familyId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FamilyRole create(Long familyId, FamilyRoleType name) {
        return new FamilyRole(null, familyId, name, null, null);
    }

    public static FamilyRole of(Long id, Long familyId, FamilyRoleType name, LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new FamilyRole(id, familyId, name, createdAt, updatedAt);
    }
}
