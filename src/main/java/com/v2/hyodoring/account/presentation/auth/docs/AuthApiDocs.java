package com.v2.hyodoring.account.presentation.auth.docs;

import com.v2.hyodoring.account.application.auth.domain.response.AccountTokenResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Auth", description = "인증 관련 API")
public interface AuthApiDocs {

    @Operation(
            summary = "소셜 로그인",
            description = """
                    ### provider(KAKAO, GOOGLE, NAVER)와 idToken을 통해 효도링에 로그인합니다.
                    * 가입되지 않은 회원의 경우 404 응답이 반환되며, 최종 회원가입 요청이 완료된 후 DB에 회원 정보가 저장됩니다.
                    * 가입된 회원의 경우 200 응답이 반환되며, 토큰(액세스 & 리프레시)과 참여 중인 가족 id 목록이 반환됩니다.
                    * 로그인이 완료된 후 Authorization 헤더에 액세스 토큰, Refresh-token 헤더에 리프레시 토큰을 포함해주세요.
                    """
    )
    ResponseEntity<CustomResponse<AccountTokenResponse>> signIn(Provider provider, String idToken);

    @Operation(
            summary = "효도링 최종 회원가입",
            description = """
                    ### 소셜 로그인 정보, 가족 정보를 DB에 저장하여 효도링에 회원가입합니다.
                    * 회원가입 후 자동으로 로그인 가능하며, 토큰(액세스 & 리프레시)과 참여 중인 가족 id 목록이 반환됩니다.
                    * 로그인이 완료된 후 Authorization 헤더에 액세스 토큰, Refresh-token 헤더에 리프레시 토큰을 포함해주세요.
                    """
    )
    ResponseEntity<CustomResponse<AccountTokenResponse>> signUp(
            Provider provider,
            String idToken,
            String familyCode,
            FamilyRoleType role
    );
}
