package com.v2.hyodoring.account.core.shared.provider;

import com.v2.hyodoring.account.core.shared.domain.Env;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class EnvProvider {
    private final Environment env;

    public EnvProvider(Environment env) {
        this.env = env;
    }

    public Env getActiveProfile() {
        return Arrays.stream(env.getActiveProfiles())
                .findFirst()
                .map(Env::fromString)
                .orElse(Env.LOCAL);
    }
}
