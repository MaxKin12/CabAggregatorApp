package com.example.driverservice.exception.custom;

import com.example.driverservice.exception.MessageSourceException;

public class DbModificationAttemptException extends MessageSourceException {

    public DbModificationAttemptException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
