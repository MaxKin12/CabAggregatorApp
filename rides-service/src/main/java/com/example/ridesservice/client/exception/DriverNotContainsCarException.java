package com.example.ridesservice.client.exception;

import com.example.ridesservice.exception.MessageSourceException;

public class DriverNotContainsCarException extends MessageSourceException {

    public DriverNotContainsCarException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
