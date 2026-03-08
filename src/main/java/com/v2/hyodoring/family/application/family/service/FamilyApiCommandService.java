package com.v2.hyodoring.family.application.family.service;

import com.v2.hyodoring.account.application.account.service.AccountQueryService;
import com.v2.hyodoring.family.application.family.domain.exception.FamilyErrorResponse;
import com.v2.hyodoring.family.application.family.domain.exception.FamilyException;
import com.v2.hyodoring.family.application.family.domain.response.FamilyPreviewResponse;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.core.family.FamilyRole;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FamilyApiCommandService {
    private final FamilyCommandService familyCommandService;
    private final FamilyQueryService familyQueryService;

    private final AccountQueryService accountQueryService;

    public FamilyPreviewResponse createNewFamily(Family family, Long accountId, FamilyRoleType role) {
        Family generated = familyCommandService.generateFamily(family, role, accountQueryService.getAccount(accountId));
        return FamilyPreviewResponse.from(generated);
    }

    public void joinFamily(FamilyRole role, Long accountId) {
        if (familyQueryService.isFamilyMember(role.getFamilyId(), accountId)) {
            throw new FamilyException(FamilyErrorResponse.FAMILY_MEMBER_ALREADY_EXISTS);
        }
        familyCommandService.joinFamily(role, accountQueryService.getAccount(accountId));
    }

}
