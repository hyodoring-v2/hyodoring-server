package com.v2.hyodoring.family.application.family.service;

import com.v2.hyodoring.family.application.family.domain.exception.FamilyErrorResponse;
import com.v2.hyodoring.family.application.family.domain.exception.FamilyException;
import com.v2.hyodoring.family.application.family.domain.response.FamilyInfoResponse;
import com.v2.hyodoring.family.application.family.domain.response.FamilyMemberResponse;
import com.v2.hyodoring.family.application.family.domain.response.FamilyPreviewResponse;
import com.v2.hyodoring.family.application.family.service.utils.FamilyCodeUtils;
import com.v2.hyodoring.family.core.family.Family;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class FamilyApiQueryService {
    private final FamilyQueryService familyQueryService;

    public void validateFamilyMember(Long accountId, Long familyId) {
        familyQueryService.validateFamilyMember(familyId, accountId);
    }

    public FamilyInfoResponse getFamilyInfo(Long familyId) {
        final Family family = familyQueryService.getFamilyInfo(familyId);
        return FamilyInfoResponse.of(family, familyQueryService.getAllFamilyMembers(familyId).stream()
                .map(member -> FamilyMemberResponse.of(
                        member.getAccountId(),
                        member.getNickname(),
                        member.getScore(),
                        member.getRole(),
                        member.getJoinedAt()
                ))
                .toList());
    }

    public String generateUniqueFamilyCode() {
        return Stream.generate(FamilyCodeUtils::generateFamilyCode)
                .limit(5) // 최대 시도 횟수 5회
                .filter(code -> !familyQueryService.existsByFamilyCode(code))
                .findFirst()
                .orElseThrow(() -> new FamilyException(FamilyErrorResponse.REDUNDANT_FAMILY_CODE));
    }

    public FamilyPreviewResponse getFamilyByCode(String code) {
        return FamilyPreviewResponse.from(familyQueryService.findByFamilyCode(code));
    }
}
