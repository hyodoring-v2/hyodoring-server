package com.v2.hyodoring.family.infrastructure.jpa.greeting.domain;

import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.Assert;

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

    @Column
    private Long senderId;

    @Column
    private Long receiverId;

    @Column(nullable = false)
    private Long writerId;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column
    private LocalDateTime checkedAt;

    public static GreetingEntity of(long familyId, long senderId, long receiverId, long writerId, String content) {
        Assert.hasText(content, "content must not be empty");
        return GreetingEntity.builder()
                .familyId(familyId)
                .senderId(senderId)
                .receiverId(receiverId)
                .writerId(writerId)
                .content(content)
                .build();
    }
}
