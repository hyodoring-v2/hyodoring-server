package com.v2.hyodoring.family.presentation.family.docs;

import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import com.v2.hyodoring.family.application.family.domain.response.FamilyInfoResponse;
import com.v2.hyodoring.family.application.family.domain.response.FamilyPreviewResponse;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Family", description = "가족 관련 API")
public interface FamilyApiDocs {

    @Operation(
            summary = "가족 코드 생성 (로그인 불필요)",
            description = "DB에 존재하는 가족 코드와 중복되지 않는 고유한 가족 코드를 생성합니다. (Return Value: 가족 코드)"
    )
    ResponseEntity<CustomResponse<String>> generateFamilyCode();

    @Operation(
            summary = "가족 코드를 통한 가족 조회 (로그인 불필요)",
            description = "가족 코드를 통해 가족 id, 가족 이름을 조회합니다."
    )
    ResponseEntity<CustomResponse<FamilyPreviewResponse>> getFamilyByCode(@PathVariable String code);

    @Operation(
            summary = "신규 가족 생성",
            description = "가족 이름과 역할을 선택한 후 신규 가족을 생성합니다."
    )
    ResponseEntity<CustomResponse<FamilyPreviewResponse>> createFamily(
            @AuthenticationPrincipal AccountPrincipal accountPrincipal,
            @RequestParam String name,
            @RequestParam FamilyRoleType role
    );

    @Operation(
            summary = "가족 구성원 정보 조회",
            description = "가족 id, 이름, 코드, 생성일, 수정일, 구성원 id, 닉네임, 레벨, 역할, 참여일을 조회합니다."
    )
    ResponseEntity<CustomResponse<FamilyInfoResponse>> getFamilyInfo(
            @AuthenticationPrincipal AccountPrincipal accountPrincipal,
            @PathVariable Long familyId
    );

    @Operation(
            summary = "새로운 가족 참여",
            description = "가족 id와 역할을 통해 새로운 가족에 참여합니다."
    )
    ResponseEntity<CustomResponse<Void>> joinFamily(
            @AuthenticationPrincipal AccountPrincipal accountPrincipal,
            @PathVariable Long familyId,
            @RequestParam FamilyRoleType role
    );
}
