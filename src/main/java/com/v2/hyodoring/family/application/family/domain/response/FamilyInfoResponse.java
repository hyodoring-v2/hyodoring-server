package com.v2.hyodoring.family.application.family.domain.response;

import com.v2.hyodoring.family.core.family.Family;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class FamilyInfoResponse {
    private final Long familyId;
    private final String name;
    private final String code;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final List<FamilyMemberResponse> members;

    private FamilyInfoResponse(Long familyId, String name, String code, LocalDateTime createdAt,
                               LocalDateTime updatedAt, List<FamilyMemberResponse> members) {
        this.familyId = familyId;
        this.name = name;
        this.code = code;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.members = members;
    }

    public static FamilyInfoResponse of(Family family, List<FamilyMemberResponse> members) {
        return new FamilyInfoResponse(
                family.getId(),
                family.getName(),
                family.getCode(),
                family.getCreatedAt(),
                family.getUpdatedAt(),
                members
        );
    }

}
