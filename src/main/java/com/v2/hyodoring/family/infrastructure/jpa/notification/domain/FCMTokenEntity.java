package com.v2.hyodoring.family.infrastructure.jpa.notification.domain;

import com.v2.hyodoring.family.core.notification.DeviceType;
import com.v2.hyodoring.family.core.notification.FCMToken;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Entity(name = "fcm_token")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_account_id_device_type", columnNames = {"account_id", "device_type"}),
                @UniqueConstraint(name = "uk_token", columnNames = {"token"})
        }
)
@Builder(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FCMTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private Long accountId;

    @Column(nullable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceType deviceType;

    @Column(nullable = false)
    private LocalDateTime lastUsedAt;

    @Column(nullable = false)
    private Boolean isActive;

    public static FCMTokenEntity from(FCMToken fcmToken) {
        return FCMTokenEntity.builder()
                .accountId(fcmToken.getAccountId())
                .token(fcmToken.getToken())
                .deviceType(fcmToken.getDeviceType())
                .lastUsedAt(fcmToken.getLastUsedAt())
                .isActive(fcmToken.getIsActive())
                .build();
    }

    public FCMToken toDomain() {
        return FCMToken.of(id, accountId, token, deviceType, lastUsedAt, isActive);
    }
}
