package com.example.passengerservice.exception.custom;

import com.example.passengerservice.exception.MessageSourceException;

public class DbModificationAttemptException extends MessageSourceException {

    public DbModificationAttemptException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
