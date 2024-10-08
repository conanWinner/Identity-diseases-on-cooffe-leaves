package com.identify.leavescoffee.exception;

import com.identify.leavescoffee.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse>  handlingException(Exception ex) {
        return ResponseEntity.badRequest().body(

                ApiResponse.builder()
                        .result(ex.getMessage())
                        .build()

        );
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse>  handlingAppException(AppException ex) {
        ErrorCode errorCode = ex.getErrorCode();

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse>  hadlingMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        String result = ex.getFieldError().getDefaultMessage();

        ErrorCode errorCode = ErrorCode.KEY_ERROR;

        try {

            errorCode = ErrorCode.valueOf(result);

        }catch (IllegalArgumentException e){

        }

        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
        );
    }

}
