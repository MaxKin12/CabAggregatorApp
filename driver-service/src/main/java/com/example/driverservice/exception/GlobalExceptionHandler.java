package com.example.driverservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalEnumArgumentException.class,
            DbModificationAttemptException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionHandlerResponse> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unknown internal server error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
