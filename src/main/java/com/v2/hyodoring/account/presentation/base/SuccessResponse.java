package com.v2.hyodoring.account.presentation.base;

import org.springframework.http.HttpStatus;

/**
 * API 요청 처리 성공 시 반환되는 응답의 구조를 정의하는 인터페이스
 * 모든 API 성공 응답은 이 인터페이스를 구현하여 일관된 형식을 유지해야 한다
 */
public interface SuccessResponse {
    HttpStatus getStatus();
    String getCode();
    String getMessage();
}
