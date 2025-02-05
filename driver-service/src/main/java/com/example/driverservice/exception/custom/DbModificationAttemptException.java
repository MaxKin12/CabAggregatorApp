package com.example.driverservice.exception.custom;

public class DbModificationAttemptException extends RuntimeException {
    public DbModificationAttemptException(String message) {
        super(message);
    }
}
