package com.v2.hyodoring.family.core.greeting;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GreetingReply {
    private final Long id;
    private final Long familyId;
    private final Long senderId;
    private final Long receiverId;
    private final String content;
    private final LocalDateTime createdAt;

    private GreetingReply(Long id, Long familyId, Long senderId, Long receiverId,
                          String content, LocalDateTime createdAt) {
        this.id = id;
        this.familyId = familyId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static GreetingReply create(Long familyId, Long senderId, Long receiverId, String content) {
        return new GreetingReply(null, familyId, senderId, receiverId, content, null);
    }

    public static GreetingReply of(Long id, Long familyId, Long senderId, Long receiverId,
                                   String content, LocalDateTime createdAt) {
        return new GreetingReply(id, familyId, senderId, receiverId, content, createdAt);
    }
}
