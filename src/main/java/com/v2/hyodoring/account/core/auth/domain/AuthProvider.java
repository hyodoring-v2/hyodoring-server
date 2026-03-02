package com.v2.hyodoring.account.core.auth.domain;

import com.v2.hyodoring.account.core.shared.domain.Env;
import lombok.Getter;

@Getter
public class AuthProvider {
    private final Long id;
    private final Provider name;
    private final Env env;

    private AuthProvider(Long id, Provider name, Env env) {
        this.id = id;
        this.name = name;
        this.env = env;
    }

    public static AuthProvider of(Long id, Provider name, Env env) {
        return new AuthProvider(id, name, env);
    }
}
