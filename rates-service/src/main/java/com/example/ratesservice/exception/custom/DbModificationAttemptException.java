package com.example.ratesservice.exception.custom;

import com.example.ratesservice.exception.MessageSourceException;

public class DbModificationAttemptException extends MessageSourceException {

    public DbModificationAttemptException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
