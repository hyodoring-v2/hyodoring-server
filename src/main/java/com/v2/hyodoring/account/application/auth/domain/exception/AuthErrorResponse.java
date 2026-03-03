package com.v2.hyodoring.account.application.auth.domain.exception;

import com.v2.hyodoring.account.application.base.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum AuthErrorResponse implements ErrorResponse {
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH4001", "존재하지 않는 계정입니다."),
    UNREGISTERED_AUTH_PROVIDER(HttpStatus.BAD_REQUEST, "AUTH4002", "등록되지 않은 인증 제공자입니다."),
    ACCOUNT_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "AUTH4003", "이미 가입된 계정입니다."),
    JWT_REFRESH_FAILED(HttpStatus.BAD_REQUEST, "AUTH4004", "액세스 토큰 재발급에 실패하였습니다."),
    INVALID_ACCESS_REFRESH_TOKEN(HttpStatus.BAD_REQUEST, "AUTH4005", "액세스 토큰과 리프레시 토큰이 유효하지 않습니다.")

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    AuthErrorResponse(HttpStatus status, String code, String message) {
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
