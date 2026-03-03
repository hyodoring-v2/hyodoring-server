package com.v2.hyodoring.account.presentation.account.docs;

import com.v2.hyodoring.account.application.account.domain.response.AccountProfileResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.infrastructure.jwt.account.domain.AccountPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Account", description = "계정 관련 API")
public interface AccountApiDocs {
    @Operation(
            summary = "계정 프로필 조회",
            description = "인증된 사용자의 계정 프로필 정보를 조회합니다."
    )
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", description = "성공적으로 계정 프로필을 조회했습니다."),
//            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다"),
//            @ApiResponse(responseCode = "401", description = "인증되지 않은 사용자입니다,"),
//            @ApiResponse(responseCode = "403", description = "권한이 없는 사용자입니다"),
//            @ApiResponse(responseCode = "404", description = "계정을 찾을 수 없습니다"),
//    })
    ResponseEntity<CustomResponse<AccountProfileResponse>> getProfile(AccountPrincipal principal);
}
