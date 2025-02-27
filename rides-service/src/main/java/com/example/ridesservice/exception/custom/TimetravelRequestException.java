package com.example.ridesservice.exception.custom;

import com.example.ridesservice.exception.MessageSourceException;

public class TimetravelRequestException extends MessageSourceException {

    public TimetravelRequestException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
