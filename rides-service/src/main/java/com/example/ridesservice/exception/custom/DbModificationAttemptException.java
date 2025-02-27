package com.example.ridesservice.exception.custom;

import com.example.ridesservice.exception.MessageSourceException;

public class DbModificationAttemptException extends MessageSourceException {

    public DbModificationAttemptException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
