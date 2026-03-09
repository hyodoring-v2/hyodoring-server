package com.v2.hyodoring.family.application.notification.domain;

import com.v2.hyodoring.account.application.base.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum NotificationErrorResponse implements ErrorResponse {
    FIREBASE_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTIFY4001", "Firebase 토큰을 조회할 수 없습니다."),

    FIREBASE_MESSAGING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "NOTIFY5001", "푸시알림 전송에 실패했습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    NotificationErrorResponse(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
