package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.application.auth.domain.request.ProviderRegistrationRequest;
import com.v2.hyodoring.account.application.auth.domain.response.AuthProviderResponse;
import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import com.v2.hyodoring.account.core.auth.domain.InternalAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalAuthApiCommandService {
    private final AuthCommandService authCommandService;

    public AuthProviderResponse registerAuthProvider(ProviderRegistrationRequest providerRegistrationRequest) {
        final AuthProvider authProvider = authCommandService.saveAuthProvider(new InternalAuthProvider(
                providerRegistrationRequest.env(),
                providerRegistrationRequest.provider(),
                providerRegistrationRequest.clientId(),
                providerRegistrationRequest.clientSecret(),
                providerRegistrationRequest.redirectUri()
        ));
        return new AuthProviderResponse(authProvider.getEnv(), authProvider.getName());
    }
}
