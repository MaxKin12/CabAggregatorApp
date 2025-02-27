package com.example.ridesservice.exception.custom;

import com.example.ridesservice.exception.MessageSourceException;

public class RideNotContainsDriverException extends MessageSourceException {

    public RideNotContainsDriverException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
