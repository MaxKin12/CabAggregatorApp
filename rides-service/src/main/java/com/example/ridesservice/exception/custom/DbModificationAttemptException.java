package com.example.ridesservice.exception.custom;

public class DbModificationAttemptException extends RuntimeException {
    public DbModificationAttemptException(String message) {
        super(message);
    }
}
