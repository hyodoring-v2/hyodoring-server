package com.v2.hyodoring.account.application.base;

import lombok.Getter;

/**
 * 공통 예외 클래스
 * 모든 커스텀 예외 클래스는 해당 클래스를 상속해야 한다
 */
@Getter
public class BaseException extends RuntimeException {
    private final ErrorResponse errorResponse;

    public BaseException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}
