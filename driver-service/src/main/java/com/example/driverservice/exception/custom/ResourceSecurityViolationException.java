package com.example.driverservice.exception.custom;

import com.example.driverservice.exception.MessageSourceException;

public class ResourceSecurityViolationException extends MessageSourceException {

    public ResourceSecurityViolationException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
