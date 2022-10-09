package com.neeejm.posts.handlers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.neeejm.posts.errors.ApiError;

@ControllerAdvice
public class AppExceptionsHandler {

    @ExceptionHandler({
        HttpMessageNotReadableException.class
    })
    public ResponseEntity<ApiError> handle(HttpMessageNotReadableException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(
            new ApiError(
                httpStatus,
                Set.of("Wrong object format")
            )
        );
    }
}
