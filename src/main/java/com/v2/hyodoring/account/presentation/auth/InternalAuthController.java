package com.v2.hyodoring.account.presentation.auth;

import com.v2.hyodoring.account.application.auth.domain.request.ProviderRegistrationRequest;
import com.v2.hyodoring.account.application.auth.domain.response.AuthProviderResponse;
import com.v2.hyodoring.account.application.auth.service.InternalAuthApiCommandService;
import com.v2.hyodoring.account.application.base.BaseSuccessResponse;
import com.v2.hyodoring.account.application.base.CustomResponse;
import com.v2.hyodoring.account.presentation.auth.docs.InternalAuthApiDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/internal/auth")
public class InternalAuthController implements InternalAuthApiDocs {
    private final InternalAuthApiCommandService internalAuthApiCommandService;

    @PostMapping("/provider")
    public ResponseEntity<CustomResponse<AuthProviderResponse>> registerAuthProvider(
            @RequestBody ProviderRegistrationRequest providerRegistrationRequest
    ) {
        providerRegistrationRequest.validate();
        return CustomResponse.onSuccess(BaseSuccessResponse.CREATED,
                internalAuthApiCommandService.registerAuthProvider(providerRegistrationRequest));
    }

}
