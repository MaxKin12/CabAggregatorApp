package com.example.driverservice.exception.custom;

import com.example.driverservice.exception.MessageSourceException;

public class EntityNotFoundException extends MessageSourceException {

    public EntityNotFoundException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
