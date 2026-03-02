package com.v2.hyodoring.account.presentation.auth;

import com.v2.hyodoring.account.application.auth.service.OIDCService;
import com.v2.hyodoring.account.application.auth.service.OIDCServiceFactory;
import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.presentation.base.BaseSuccessResponse;
import com.v2.hyodoring.account.presentation.base.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final OIDCServiceFactory oidcServiceFactory;

    @PostMapping("/oauth/{provider}")
    public ResponseEntity<CustomResponse<Void>> signIn(
            @RequestParam String idToken,
            @PathVariable AuthProvider provider) {
        final OIDCService oidcService = oidcServiceFactory.getOIDCService(provider);
        oidcService.signIn(idToken);
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED);
    }
}
