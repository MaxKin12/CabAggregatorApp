package com.example.ridesservice.exception;

public class DbModificationAttemptException extends RuntimeException {
    public DbModificationAttemptException(String message) {
        super(message);
    }
}
