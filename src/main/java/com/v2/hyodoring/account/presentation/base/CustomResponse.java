package com.v2.hyodoring.account.presentation.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@JsonPropertyOrder({"status", "code", "message", "data"})
public class CustomResponse<T> {
    private final int status;
    private final String code;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final T data;

    private CustomResponse(int status, String code, String message, T data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResponseEntity<CustomResponse<T>> onSuccess(SuccessResponse successResponse) {
        return ResponseEntity.status(successResponse.getStatus().value())
                .body(new CustomResponse<>(successResponse.getStatus().value(),
                        successResponse.getCode(), successResponse.getMessage(), null));
    }

    public static <T> ResponseEntity<CustomResponse<T>> onSuccess(SuccessResponse successResponse, T data) {
        return ResponseEntity.status(successResponse.getStatus().value())
                .body(new CustomResponse<>(successResponse.getStatus().value(),
                        successResponse.getCode(), successResponse.getMessage(), data));
    }

    public static <T> ResponseEntity<CustomResponse<T>> onFailure(ErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatus().value())
                .body(new CustomResponse<>(errorResponse.getStatus().value(),
                        errorResponse.getCode(), errorResponse.getMessage(), null));
    }

    public static <T> ResponseEntity<CustomResponse<T>> onFailure(ErrorResponse errorResponse, T data) {
        return ResponseEntity.status(errorResponse.getStatus().value())
                .body(new CustomResponse<>(errorResponse.getStatus().value(),
                        errorResponse.getCode(), errorResponse.getMessage(), data));
    }
}
