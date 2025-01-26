package com.example.passengerservice.exception;

public class DBModificationAttemptException extends RuntimeException {
    public DBModificationAttemptException(String message) {
        super(message);
    }
}
