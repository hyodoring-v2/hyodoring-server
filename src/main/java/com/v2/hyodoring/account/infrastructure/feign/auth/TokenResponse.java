package com.v2.hyodoring.account.infrastructure.feign.auth;

import lombok.Getter;

/**
 * 미사용
 */
@Getter
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;
}
