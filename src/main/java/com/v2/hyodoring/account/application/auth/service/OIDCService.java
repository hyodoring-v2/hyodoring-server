package com.v2.hyodoring.account.application.auth.service;

import com.v2.hyodoring.account.core.auth.domain.AuthProvider;

public interface OIDCService {
    boolean supports(AuthProvider provider);
    void signIn(String idToken);
}
