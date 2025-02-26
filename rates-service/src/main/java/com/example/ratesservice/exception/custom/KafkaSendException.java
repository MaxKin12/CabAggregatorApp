package com.example.ratesservice.exception.custom;

import com.example.ratesservice.exception.MessageSourceException;

public class KafkaSendException extends MessageSourceException {

    public KafkaSendException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
