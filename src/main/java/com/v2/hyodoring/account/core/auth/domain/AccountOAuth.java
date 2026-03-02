package com.v2.hyodoring.account.core.auth.domain;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AccountOAuth {
    private final Long id;
    private final Long accountId;
    private final Long providerId;
    private final String subject;
    private final String accessToken;
    private final String refreshToken;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    private AccountOAuth(Long id, Long accountId, Long providerId,
                         String subject, String accessToken, String refreshToken, String email,
                         LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.accountId = accountId;
        this.providerId = providerId;
        this.subject = subject;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static AccountOAuth create(Long accountId, Long providerId, String subject,
                                      String accessToken, String refreshToken, String email) {
        return new AccountOAuth(null, accountId, providerId, subject, accessToken, refreshToken, email, null, null);
    }

    public static AccountOAuth of(Long id, Long accountId, Long providerId, String subject,
                                  String accessToken, String refreshToken, String email,
                                  LocalDateTime createdAt, LocalDateTime updatedAt) {
        return new AccountOAuth(id, accountId, providerId, subject, accessToken, refreshToken, email, createdAt, updatedAt);
    }
}
