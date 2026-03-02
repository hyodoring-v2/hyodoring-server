package com.v2.hyodoring.account.infrastructure.feign.auth.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class OIDCPayload {
    private final String subject;
    private final String email;
    private final String name;

    private OIDCPayload(
            String subject,
            String email,
            String name
    ) {
        Assert.hasText(subject, "subject must not be empty");
        this.subject = subject;
        this.email = email;
        this.name = name;
    }

    public static OIDCPayload of(
            String subject,
            String email,
            String name
    ) {
        return new OIDCPayload(subject, email, name);
    }
}
