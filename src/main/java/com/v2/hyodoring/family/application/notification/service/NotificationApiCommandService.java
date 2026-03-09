package com.v2.hyodoring.family.application.notification.service;

import com.v2.hyodoring.account.application.account.service.AccountQueryService;
import com.v2.hyodoring.family.core.notification.DeviceType;
import com.v2.hyodoring.family.core.notification.FCMToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationApiCommandService {
    private final AccountQueryService accountQueryService;
    private final FCMCommandService fcmCommandService;

    public FCMToken saveFCMToken(Long accountId, String token) {
        // 존재하는 사용자인지 확인
        accountQueryService.getActiveAccount(accountId);
        // FCM 토큰을 DB에 저장
        return fcmCommandService.saveToken(FCMToken
                .create(accountId, token, DeviceType.ANDROID));
    }
}
