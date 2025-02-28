package com.example.ratesservice.exception.custom;

import com.example.ratesservice.exception.MessageSourceException;

public class RateListIsEmptyException extends MessageSourceException {

    public RateListIsEmptyException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
