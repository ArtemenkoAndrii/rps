package com.example.rps.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class ErrorController {
    private static final String UNKNOWN = "Oops! Something went wrong";
    private static final String INVALID_REQUEST = "Unsupported request parameter";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleError(Exception e) {
        return createError(INTERNAL_SERVER_ERROR, UNKNOWN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleValidation(Exception e) {
        return createError(BAD_REQUEST, INVALID_REQUEST);
    }

    private ResponseEntity<ErrorResponse> createError(final HttpStatus status, final String detail) {
        var response = ResponseMapper.mapError(detail);
        return new ResponseEntity<>(response, status);
    }
}
