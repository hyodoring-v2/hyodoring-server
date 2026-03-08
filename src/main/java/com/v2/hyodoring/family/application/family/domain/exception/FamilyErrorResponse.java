package com.v2.hyodoring.family.application.family.domain.exception;

import com.v2.hyodoring.account.application.base.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum FamilyErrorResponse implements ErrorResponse {
    FAMILY_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY4001", "가족을 조회할 수 없습니다."),
    REDUNDANT_FAMILY_CODE(HttpStatus.CONFLICT, "FAMILY4002", "가족 코드가 중복되었습니다. 잠시 후 다시 시도해주세요."),
    FAMILY_MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY4003", "가족 구성원을 조회할 수 없습니다."),
    FAMILY_ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "FAMILY4004", "가족 구성원 역할을 조회할 수 없습니다."),
    FAMILY_MEMBER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "FAMILY4005", "이미 가족의 구성원입니다."),
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
