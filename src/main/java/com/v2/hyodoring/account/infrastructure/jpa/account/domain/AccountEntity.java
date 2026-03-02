package com.v2.hyodoring.account.infrastructure.jpa.account.domain;

import com.v2.hyodoring.account.core.account.domain.Account;
import com.v2.hyodoring.account.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity(name = "account")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AccountEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String nickname;

    @Column
    private LocalDateTime inactiveAt;

    public boolean isInactive() {
        return inactiveAt != null;
    }

    public static AccountEntity from(Account account) {
        return AccountEntity.builder()
                .nickname(account.getNickname())
                .build();
    }

    public Account toDomain() {
        return Account.of(id, nickname, getCreatedAt(), getUpdatedAt(), inactiveAt);
    }
}
