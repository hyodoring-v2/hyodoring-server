package com.v2.hyodoring.account.presentation.base;

import org.springframework.http.HttpStatus;

public enum BaseErrorResponse implements ErrorResponse {
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "COMMON4001", "잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON4002", "인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON4003", "접근이 거부되었습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON4004", "리소스를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON5001", "서버 내부 오류입니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    BaseErrorResponse(HttpStatus status, String code, String message) {
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
