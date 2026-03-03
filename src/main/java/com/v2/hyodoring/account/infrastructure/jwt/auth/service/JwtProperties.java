package com.v2.hyodoring.account.infrastructure.jwt.auth.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String prefix,
        String secret,
        AccessToken accessToken,
        RefreshToken refreshToken
) {
    record AccessToken(long expirationMs) {}
    record RefreshToken(long expirationMs) {}
}