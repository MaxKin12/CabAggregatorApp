package com.example.ridesservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<RideException> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new RideException(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DbModificationAttemptException.class)
    public ResponseEntity<RideException> handleDBException(Exception e) {
        return new ResponseEntity<>(new RideException(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RideException> handleValidationException(Exception e) {
        return new ResponseEntity<>(new RideException(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<RideException> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new RideException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unknown internal server error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
