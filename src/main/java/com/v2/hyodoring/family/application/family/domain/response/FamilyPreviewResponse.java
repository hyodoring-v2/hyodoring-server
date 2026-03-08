package com.v2.hyodoring.family.application.family.domain.response;

import com.v2.hyodoring.family.core.family.Family;
import lombok.Getter;

@Getter
public class FamilyPreviewResponse {
    private final Long familyId;
    private final String name;
    private final String code;

    private FamilyPreviewResponse(Long familyId, String name, String code) {
        this.familyId = familyId;
        this.name = name;
        this.code = code;
    }

    public static FamilyPreviewResponse from(Family family) {
        return new FamilyPreviewResponse(family.getId(), family.getName(), family.getCode());
    }
}
