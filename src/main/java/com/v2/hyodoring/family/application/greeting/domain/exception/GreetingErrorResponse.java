package com.v2.hyodoring.family.application.greeting.domain.exception;

import com.v2.hyodoring.account.application.base.ErrorResponse;
import org.springframework.http.HttpStatus;

public enum GreetingErrorResponse implements ErrorResponse {
    GREETING_REQUEST_NOT_FOUND(HttpStatus.NOT_FOUND, "GREET4001", "안부 요청이 존재하지 않습니다."),
    GREETING_REPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "GREET4002", "안부 메시지가 존재하지 않습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    GreetingErrorResponse(HttpStatus status, String code, String message) {
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
