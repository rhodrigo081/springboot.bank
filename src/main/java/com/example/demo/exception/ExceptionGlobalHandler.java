package com.example.demo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionGlobalHandler extends RuntimeException {

    private final Logger logger = LoggerFactory.getLogger(ExceptionGlobalHandler.class);

    public record ErrorResponse(String message, String error, String statusValue) {
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {

        String error = "Invalid argument";
        String message = e.getMessage();
        String statusValue = String.valueOf(HttpStatus.UNPROCESSABLE_ENTITY.value());

        logger.error(message, e);

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse(message, error, statusValue));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> noSuchElementException(NoSuchElementException e) {
        String error = "Resource identifier not found";
        String message = e.getMessage();
        String statusValue = String.valueOf(HttpStatus.NOT_FOUND.value());

        logger.error(message, e);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(message, error, statusValue));
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponse> throwable(Throwable e) {
        String error = "Internal Server Error";
        String message = e.getMessage();
        String statusValue = String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value());

        logger.error(message, e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(error, message, statusValue));
    }
}