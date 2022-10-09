package com.neeejm.posts.handlers;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.neeejm.posts.errors.ApiError;
import com.neeejm.posts.exceptions.EmptyPostException;
import com.neeejm.posts.exceptions.PostNotFoundException;

@ControllerAdvice
public class PostExceptionsHandler {

    @ExceptionHandler({
        PostNotFoundException.class,
    })
    public ResponseEntity<ApiError> handle(PostNotFoundException exception) {
        HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(httpStatus).body(
            new ApiError(
                httpStatus,
                Set.of(exception.getMessage())
            )
        );
    }

    @ExceptionHandler({
        EmptyPostException.class,
    })
    public ResponseEntity<ApiError> handle(EmptyPostException exception) {
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(httpStatus).body(
            new ApiError(
                httpStatus,
                Set.of(exception.getMessage())
            )
        );
    }
}
