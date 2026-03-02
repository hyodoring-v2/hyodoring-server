package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.core.auth.domain.AuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OIDCServiceFactory {
    private final List<OIDCService> oidcServices;

    public OIDCService getOIDCService(AuthProvider provider) {
        return oidcServices.stream()
                .filter(service -> service.supports(provider))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported auth provider: " + provider));
    }
}
