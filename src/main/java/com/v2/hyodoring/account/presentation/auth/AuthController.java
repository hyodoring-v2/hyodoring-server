package com.v2.hyodoring.account.presentation.auth;

import com.v2.hyodoring.account.application.auth.domain.response.AccountTokenResponse;
import com.v2.hyodoring.account.application.auth.service.AuthApiCommandService;
import com.v2.hyodoring.account.core.auth.domain.Provider;
import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.presentation.auth.docs.AuthApiDocs;
import com.v2.hyodoring.family.core.role.FamilyRoleType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController implements AuthApiDocs {

    private final AuthApiCommandService authApiCommandService;

    @PostMapping("/oauth/{provider}")
    public ResponseEntity<CustomResponse<AccountTokenResponse>> signIn(
            @PathVariable Provider provider,
            @RequestParam String idToken
    ) {
        final AccountTokenResponse accountTokenResponse = authApiCommandService.signIn(provider, idToken);
        return CustomResponse.onSuccess(BaseSuccessResponse.OK, accountTokenResponse);
    }

    @PostMapping("/oauth/{provider}/sign-up")
    public ResponseEntity<CustomResponse<AccountTokenResponse>> signUp(
            @PathVariable Provider provider,
            @RequestParam String idToken,
            @RequestParam String familyCode,
            @RequestParam FamilyRoleType role
    ) {
        final AccountTokenResponse accountTokenResponse = authApiCommandService.signUp(provider, idToken, familyCode, role);
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED, accountTokenResponse);
    }
}
