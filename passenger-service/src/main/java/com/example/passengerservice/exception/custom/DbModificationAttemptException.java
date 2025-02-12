package com.example.passengerservice.exception.custom;

public class DbModificationAttemptException extends RuntimeException {

    public DbModificationAttemptException(String message) {
        super(message);
    }

}
