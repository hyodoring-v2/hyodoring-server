package com.v2.hyodoring.family.infrastructure.jpa.notification.domain;

import com.v2.hyodoring.family.infrastructure.jpa.base.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity(name = "notification")
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NotificationEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private Boolean greetingArrival = false;    // 안부 도착 알림

    @Column(nullable = false)
    private Boolean greetingSuggestion = false; // 안부 발송 제안 알림

    public static NotificationEntity of(long accountId, boolean greetingArrival, boolean greetingSuggestion) {
        return NotificationEntity.builder()
                .accountId(accountId)
                .greetingArrival(greetingArrival)
                .greetingSuggestion(greetingSuggestion)
                .build();
    }
}
