package com.v2.hyodoring.account.presentation.base;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@Hidden
@RestControllerAdvice
public class BaseExceptionAdvice extends ResponseEntityExceptionHandler {

    /**
     * BaseException과 하위 예외를 처리하는 공통 예외 처리 메서드
     * @param e BaseException 또는 그 하위 예외 객체
     * @return ApiResponse 객체를 포함하는 ResponseEntity, 실패 응답으로 반환
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<CustomResponse<Void>> handleBaseException(BaseException e) {
        log.error("EXCP:CTRL:GE__:::GeneralException msg({})", e.getErrorResponse().getMessage());
        return CustomResponse.onFailure(e.getErrorResponse());
    }

    /**
     * IllegalArgumentException을 처리하는 예외 처리 메서드
     * @param e IllegalArgumentException 객체
     * @return BAD_REQUEST 응답으로 반환
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<CustomResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("EXCP:CTRL:IAE_:::IllegalArgumentException msg({})", e.getMessage());
        return CustomResponse.onFailure(BaseErrorResponse.INVALID_REQUEST);
    }

    /**
     * IllegalStateException을 처리하는 예외 처리 메서드
     * @param e IllegalStateException 객체
     * @return INTERNAL_SERVER_ERROR 응답으로 반환
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<CustomResponse<Void>> handleException(IllegalStateException e) {
        log.error("EXCP:CTRL:ISE_:::IllegalStateException msg({})", e.getMessage());
        return CustomResponse.onFailure(BaseErrorResponse.INTERNAL_SERVER_ERROR);
    }
}
