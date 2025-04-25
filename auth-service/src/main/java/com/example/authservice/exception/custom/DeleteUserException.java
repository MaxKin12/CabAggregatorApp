package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class DeleteUserException extends MessageSourceException {

    public DeleteUserException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
