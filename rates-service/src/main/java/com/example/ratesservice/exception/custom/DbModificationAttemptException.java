package com.example.ratesservice.exception.custom;

public class DbModificationAttemptException extends RuntimeException {

    public DbModificationAttemptException(String message) {
        super(message);
    }

}
