package com.v2.hyodoring.account.core.account.domain;

import com.v2.hyodoring.account.application.account.utils.NicknameGenerator;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

@Getter
public class Account {
    private final Long id;
    private final String nickname;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final LocalDateTime inactiveAt;

    private Account(Long id, String nickname, LocalDateTime createdAt,
                    LocalDateTime updatedAt, LocalDateTime inactiveAt) {
        this.id = id;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.inactiveAt = inactiveAt;
    }

    public boolean isActive() {
        return inactiveAt == null;
    }

    public static Account create(String nickname) {
        if (StringUtils.hasText(nickname)) {
            return new Account(null, NicknameGenerator.generateNickname(), null, null, null);
        }
        return new Account(null, nickname, null, null, null);
    }

    public static Account of(Long id, String nickname, LocalDateTime createdAt,
                      LocalDateTime updatedAt, LocalDateTime inactiveAt) {
        return new Account(id, nickname, createdAt, updatedAt, inactiveAt);
    }
}
