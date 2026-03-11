package com.v2.hyodoring.family.application.notification.service;

import com.v2.hyodoring.family.application.notification.domain.NotificationErrorResponse;
import com.v2.hyodoring.family.application.notification.domain.NotificationException;
import com.v2.hyodoring.family.core.notification.DeviceType;
import com.v2.hyodoring.family.core.notification.FCMToken;
import com.v2.hyodoring.family.infrastructure.fcm.base.FCMClient;
import com.v2.hyodoring.family.infrastructure.jpa.notification.domain.FCMTokenEntity;
import com.v2.hyodoring.family.infrastructure.jpa.notification.repository.FCMTokenCommandRepository;
import com.v2.hyodoring.family.infrastructure.jpa.notification.repository.FCMTokenQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class FCMCommandService {
    private final FCMClient fcmClient;
    private final FCMTokenCommandRepository fcmTokenCommandRepository;
    private final FCMTokenQueryRepository fcmTokenQueryRepository;

    public FCMToken saveToken(FCMToken fcmToken) {
        return fcmTokenCommandRepository.save(FCMTokenEntity.from(fcmToken)).toDomain();
    }

    public void sendMessage(Long receiverId, String title, String body) {
        // FCM Token 조회
        final FCMToken fcmToken = fcmTokenQueryRepository.findByAccountIdAndDeviceType(receiverId, DeviceType.ANDROID)
                    .orElseThrow(() -> new NotificationException(NotificationErrorResponse.FIREBASE_TOKEN_NOT_FOUND))
                .toDomain();
        // 메시지 본문 생성
        fcmClient.sendMessage(fcmToken.getToken(), title, body);
    }
}
