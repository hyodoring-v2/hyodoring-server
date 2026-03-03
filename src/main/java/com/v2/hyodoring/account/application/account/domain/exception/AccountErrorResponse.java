package com.v2.hyodoring.account.application.account.domain.exception;

import com.v2.hyodoring.account.application.base.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum AccountErrorResponse implements ErrorResponse {
    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "ACCOUNT4001", "사용자를 찾을 수 없습니다."),

    ACCOUNT_ROLE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "ACCOUNT5001", "사용자 권한 정보가 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    AccountErrorResponse(HttpStatus status, String code, String message) {
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
