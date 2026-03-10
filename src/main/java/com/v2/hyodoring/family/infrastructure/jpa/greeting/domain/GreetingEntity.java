package com.v2.hyodoring.family.infrastructure.jpa.greeting.domain;

import com.v2.hyodoring.family.core.greeting.Greeting;
import com.v2.hyodoring.family.core.greeting.GreetingReply;
import com.v2.hyodoring.family.core.greeting.GreetingType;
import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity(name = "greeting")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GreetingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long familyId;

    @Column(nullable = false)
    private Long senderId;

    @Column(nullable = false)
    private Long receiverId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GreetingType type;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column
    private LocalDateTime checkedAt;

    public static GreetingEntity from(GreetingReply greetingReply) {
        return GreetingEntity.builder()
                .familyId(greetingReply.getFamilyId())
                .senderId(greetingReply.getSenderId())
                .receiverId(greetingReply.getReceiverId())
                .type(GreetingType.REPLY)
                .content(greetingReply.getContent())
                .build();
    }

    public static GreetingEntity from(Greeting greeting) {
        return GreetingEntity.builder()
                .familyId(greeting.getFamilyId())
                .senderId(greeting.getSenderId())
                .receiverId(greeting.getReceiverId())
                .type(GreetingType.REQUEST)
                .content(greeting.getContent())
                .checkedAt(greeting.getCheckedAt())
                .build();
    }

    public GreetingReply toGreetingReply() {
        if (!GreetingType.REPLY.equals(type)) {
            throw new IllegalArgumentException("greetingType must be reply");
        }
        return GreetingReply.of(id, familyId, senderId, receiverId, content, getCreatedAt());
    }

    public Greeting toGreeting() {
        if (!GreetingType.REQUEST.equals(type)) {
            throw new IllegalArgumentException("greetingType must be request");
        }
        return Greeting.of(id, familyId, senderId, receiverId, content, getCreatedAt(), checkedAt);
    }

    public boolean isChecked() {
        return checkedAt != null;
    }

    public void updateCheckStatus() {
        checkedAt = LocalDateTime.now();
    }
}
