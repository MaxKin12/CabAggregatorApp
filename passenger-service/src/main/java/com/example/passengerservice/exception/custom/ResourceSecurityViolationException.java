package com.example.passengerservice.exception.custom;

import com.example.passengerservice.exception.MessageSourceException;

public class ResourceSecurityViolationException extends MessageSourceException {

    public ResourceSecurityViolationException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
