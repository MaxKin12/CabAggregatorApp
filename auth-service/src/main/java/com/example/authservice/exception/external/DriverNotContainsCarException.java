package com.example.authservice.exception.external;

import com.example.authservice.exception.MessageSourceException;

public class DriverNotContainsCarException extends MessageSourceException {

    public DriverNotContainsCarException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
