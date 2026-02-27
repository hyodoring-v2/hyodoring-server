package com.v2.hyodoring.account.infrastructure.jpa.account.domain;

import com.v2.hyodoring.account.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

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

    public static AccountEntity of(String nickname) {
        Assert.hasText(nickname, "nickname must not be empty");
        return AccountEntity.builder()
                .nickname(nickname)
                .build();
    }
}
