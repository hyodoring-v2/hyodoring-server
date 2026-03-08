package com.v2.hyodoring.family.presentation.family;

import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import com.v2.hyodoring.family.application.family.domain.response.FamilyInfoResponse;
import com.v2.hyodoring.family.application.family.domain.response.FamilyPreviewResponse;
import com.v2.hyodoring.family.application.family.service.FamilyApiCommandService;
import com.v2.hyodoring.family.application.family.service.FamilyApiQueryService;
import com.v2.hyodoring.family.core.family.Family;
import com.v2.hyodoring.family.core.family.FamilyRole;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import com.v2.hyodoring.family.presentation.family.docs.FamilyApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/family")
public class FamilyController implements FamilyApiDocs {

    private final FamilyApiCommandService familyApiCommandService;
    private final FamilyApiQueryService familyApiQueryService;

    // 로그인 불필요한 API //

    @PostMapping("/code")
    public ResponseEntity<CustomResponse<String>> generateFamilyCode() {
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED,
                familyApiQueryService.generateUniqueFamilyCode());
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<CustomResponse<FamilyPreviewResponse>> getFamilyByCode(@PathVariable String code) {
        return CustomResponse.onSuccess(BaseSuccessResponse.OK, familyApiQueryService.getFamilyByCode(code));
    }

    // 로그인 필요한 API //

    @PostMapping
    public ResponseEntity<CustomResponse<FamilyPreviewResponse>> createFamily(
            @AuthenticationPrincipal AccountPrincipal accountPrincipal,
            @RequestParam String name,
            @RequestParam FamilyRoleType role
    ) {
        final String code = familyApiQueryService.generateUniqueFamilyCode();
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED, familyApiCommandService
                .createNewFamily(Family.create(name, code), accountPrincipal.getId(), role));
    }

    @GetMapping("/{familyId}/members")
    public ResponseEntity<CustomResponse<FamilyInfoResponse>> getFamilyInfo(
            @AuthenticationPrincipal AccountPrincipal accountPrincipal,
            @PathVariable Long familyId
    ) {
        familyApiQueryService.validateFamilyMember(accountPrincipal.getId(), familyId);
        return CustomResponse.onSuccess(BaseSuccessResponse.OK, familyApiQueryService.getFamilyInfo(familyId));
    }

    @PostMapping("/{familyId}/members")
    public ResponseEntity<CustomResponse<Void>> joinFamily(
            @AuthenticationPrincipal AccountPrincipal accountPrincipal,
            @PathVariable Long familyId,
            @RequestParam FamilyRoleType role
    ) {
        final Long accountId = accountPrincipal.getId();
        familyApiCommandService.joinFamily(FamilyRole.create(accountId, familyId, role), accountId);
        return CustomResponse.onSuccess(BaseSuccessResponse.OK);
    }
}
