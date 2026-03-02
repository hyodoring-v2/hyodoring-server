package com.v2.hyodoring.family.application.family.exception;

import com.v2.hyodoring.account.application.base.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FamilyErrorResponse implements ErrorResponse {
    FAMILY_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY4001", "가족을 조회할 수 없습니다."),
    ;

    private final HttpStatus status;
    private final String code;
    private final String message;

    FamilyErrorResponse(HttpStatus status, String code, String message) {
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
