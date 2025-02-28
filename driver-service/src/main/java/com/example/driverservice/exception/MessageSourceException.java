package com.example.driverservice.exception;

import lombok.Getter;

@Getter
public class MessageSourceException extends RuntimeException {

    private final String messageKey;
    private final String[] args;

    public MessageSourceException(String messageKey, String... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

}
