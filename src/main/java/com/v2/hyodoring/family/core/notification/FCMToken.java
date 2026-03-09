package com.v2.hyodoring.family.core.notification;

import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
public class FCMToken {
    private final Long id;
    private final Long accountId;
    private final String token;
    private final DeviceType deviceType;
    private final LocalDateTime lastUsedAt;
    private final Boolean isActive;

    private FCMToken(Long id, long accountId, String token, DeviceType deviceType,
                     LocalDateTime lastUsedAt, boolean isActive) {
        Assert.hasText(token, "token can not be empty");
        Assert.notNull(deviceType, "deviceType can not be null");
        Assert.notNull(lastUsedAt, "lastUsedAt can not be null");
        this.id = id;
        this.accountId = accountId;
        this.token = token;
        this.deviceType = deviceType;
        this.lastUsedAt = lastUsedAt;
        this.isActive = isActive;
    }

    public static FCMToken create(long accountId, String token, DeviceType deviceType) {
        return new FCMToken(null, accountId, token, deviceType, LocalDateTime.now(), true);
    }

    public static FCMToken of(long id, long accountId, String token, DeviceType deviceType,
                              LocalDateTime lastUsedAt, boolean isActive) {
        return new FCMToken(id, accountId, token, deviceType, lastUsedAt, isActive);
    }
}
