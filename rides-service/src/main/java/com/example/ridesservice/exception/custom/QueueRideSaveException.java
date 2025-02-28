package com.example.ridesservice.exception.custom;

import com.example.ridesservice.exception.MessageSourceException;

public class QueueRideSaveException extends MessageSourceException {

    public QueueRideSaveException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
