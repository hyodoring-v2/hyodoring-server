package com.v2.hyodoring.family.core.greeting;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Greeting {
    private final Long id;
    private final Long familyId;
    private final Long senderId;
    private final Long receiverId;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime checkedAt;

    private Greeting(Long id, Long familyId, Long senderId, Long receiverId,
                     String content, LocalDateTime createdAt, LocalDateTime checkedAt) {
        this.id = id;
        this.familyId = familyId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createdAt = createdAt;
        this.checkedAt = checkedAt;
    }

    public static Greeting create(Long familyId, Long senderId, Long receiverId, String content) {
        return new Greeting(null, familyId, senderId, receiverId, content, null, null);
    }

    public static Greeting of(Long id, Long familyId, Long senderId, Long receiverId,
                              String content, LocalDateTime createdAt, LocalDateTime checkedAt) {
        return new Greeting(id, familyId, senderId, receiverId, content, createdAt, checkedAt);
    }
}
