package com.example.driverservice.exception;

public class DBModificationAttemptException extends RuntimeException {
    public DBModificationAttemptException(String message) {
        super(message);
    }
}
