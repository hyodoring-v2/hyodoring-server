package com.v2.hyodoring.account.presentation.base;

import org.springframework.http.HttpStatus;

public enum BaseSuccessResponse implements SuccessResponse {
    OK(HttpStatus.OK, "COMMON2001", "요청이 성공적으로 처리되었습니다."),
    CREATED(HttpStatus.CREATED, "COMMON2002", "리소스가 성공적으로 생성되었습니다."),
    ACCEPTED(HttpStatus.ACCEPTED, "COMMON2003", "요청이 성공적으로 접수되었습니다."),
    NO_CONTENT(HttpStatus.NO_CONTENT, "COMMON2004", "요청이 성공적으로 처리되었지만 반환할 콘텐츠가 없습니다."),

    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    BaseSuccessResponse(HttpStatus status, String code, String message) {
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
