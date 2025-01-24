package com.example.passengerservice.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<PassengerException> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new PassengerException(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(DBException.class)
    public ResponseEntity<PassengerException> handleDBException(Exception e) {
        return new ResponseEntity<>(new PassengerException(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<PassengerException> handleValidationException(Exception e) {
        return new ResponseEntity<>(new PassengerException(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PassengerException> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new PassengerException(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Unknown internal server error: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
