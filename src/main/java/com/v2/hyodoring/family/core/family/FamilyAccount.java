package com.v2.hyodoring.family.core.family;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class FamilyAccount {
    private final Long id;
    private final Long familyId;
    private final Long accountId;
    private final Integer score;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private FamilyAccount(Long id, Long familyId, Long accountId, Integer score,
                          LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.familyId = familyId;
        this.accountId = accountId;
        this.score = score;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static FamilyAccount create(Long familyId, Long accountId) {
        return new FamilyAccount(null, familyId, accountId, 50, null, null);
    }

    public static FamilyAccount of(Long id, Long familyId, Long accountId, Integer score,
                                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new FamilyAccount(id, familyId, accountId, score, createdAt, updatedAt);
    }
}
