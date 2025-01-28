package com.example.driverservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<DriverServiceException> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new DriverServiceException(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DBModificationAttemptException.class)
    public ResponseEntity<DriverServiceException> handleDBException(Exception e) {
        return new ResponseEntity<>(new DriverServiceException(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<DriverServiceException> handleValidationException(Exception e) {
        return new ResponseEntity<>(new DriverServiceException(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<DriverServiceException> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new DriverServiceException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unknown internal server error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
