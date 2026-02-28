package com.v2.hyodoring.account.infrastructure.jpa.auth.domain;

import com.v2.hyodoring.account.core.auth.domain.AuthType;
import com.v2.hyodoring.account.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Getter
@Entity(name = "account_auth")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_account_auth", columnNames = {"account_id", "auth_type"})
        }
)
@Builder(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class AccountAuthEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Long authId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthType authType;

    public static AccountAuthEntity of(long accountId, long authId, AuthType authType) {
        Assert.notNull(authType, "authType can not be null");
        return AccountAuthEntity.builder()
                .accountId(accountId)
                .authId(authId)
                .authType(authType)
                .build();
    }
}
