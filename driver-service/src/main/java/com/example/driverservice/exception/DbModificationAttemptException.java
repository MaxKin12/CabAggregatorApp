package com.example.driverservice.exception;

public class DbModificationAttemptException extends RuntimeException {
    public DbModificationAttemptException(String message) {
        super(message);
    }
}
