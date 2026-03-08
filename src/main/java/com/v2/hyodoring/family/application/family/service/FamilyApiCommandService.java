package com.v2.hyodoring.family.application.family.service;

import com.v2.hyodoring.account.application.account.service.AccountQueryService;
import com.v2.hyodoring.family.application.family.domain.response.FamilyPreviewResponse;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.core.family.FamilyRole;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FamilyApiCommandService {
    private final FamilyCommandService familyCommandService;
    private final FamilyQueryService familyQueryService;

    private final AccountQueryService accountQueryService;

    public FamilyPreviewResponse createNewFamily(Family family, Long accountId, FamilyRoleType role) {
        Family generated = familyCommandService.generateFamily(family, role, accountQueryService.getAccount(accountId));
        return FamilyPreviewResponse.from(generated);
    }

    public void joinFamily(FamilyRole role, Long accountId) {
        //TODO: 이미 참여중인 가족엔 참여할 수 없도록 예외처리
        familyCommandService.joinFamily(role, accountQueryService.getAccount(accountId));
    }

}
