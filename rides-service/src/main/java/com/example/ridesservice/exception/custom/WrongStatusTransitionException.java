package com.example.ridesservice.exception.custom;

import com.example.ridesservice.exception.MessageSourceException;

public class WrongStatusTransitionException extends MessageSourceException {

    public WrongStatusTransitionException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
