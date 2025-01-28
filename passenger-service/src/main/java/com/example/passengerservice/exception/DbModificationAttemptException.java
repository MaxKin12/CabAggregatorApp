package com.example.passengerservice.exception;

public class DbModificationAttemptException extends RuntimeException {
    public DbModificationAttemptException(String message) {
        super(message);
    }
}
