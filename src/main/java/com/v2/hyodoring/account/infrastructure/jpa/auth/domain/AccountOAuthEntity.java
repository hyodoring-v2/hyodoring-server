package com.v2.hyodoring.account.infrastructure.jpa.auth.domain;

import com.v2.hyodoring.account.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "account_oauth")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_account_provider", columnNames = {"account_id", "provider_id"}),
                @UniqueConstraint(name = "uk_provider_subject", columnNames = {"provider_id", "subject"})
        })
@Builder(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AccountOAuthEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long providerId;

    @Column(nullable = false)
    private String subject;

    @Column(columnDefinition = "text")
    private String accessToken;

    @Column(columnDefinition = "text")
    private String refreshToken;

    @Column(columnDefinition = "text")
    private String email;

    public static AccountOAuthEntity of(long accountId, long providerId,
                                        String accessToken, String refreshToken,
                                        String email) {
        return AccountOAuthEntity.builder()
                .accountId(accountId)
                .providerId(providerId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(email)
                .build();
    }
}
