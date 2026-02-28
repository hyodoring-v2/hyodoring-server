package com.v2.hyodoring.account.core.shared.domain;

import java.util.Arrays;

public enum Env {
    LOCAL,
    DEV,
    STG,
    PROD,
    ;

    public static Env fromString(String envString) {
        return Arrays.stream(Env.values())
                .filter(env -> env.name().equalsIgnoreCase(envString))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid environment: " + envString));
    }
}
