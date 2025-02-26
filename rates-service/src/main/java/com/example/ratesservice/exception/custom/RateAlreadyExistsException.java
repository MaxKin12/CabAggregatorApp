package com.example.ratesservice.exception.custom;

import com.example.ratesservice.exception.MessageSourceException;

public class RateAlreadyExistsException extends MessageSourceException {

    public RateAlreadyExistsException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
