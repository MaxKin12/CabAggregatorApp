package com.example.ratesservice.exception.custom;

import com.example.ratesservice.exception.MessageSourceException;

public class RateNotFoundException extends MessageSourceException {

    public RateNotFoundException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
