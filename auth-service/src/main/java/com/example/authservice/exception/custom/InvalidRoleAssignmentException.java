package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class InvalidRoleAssignmentException extends MessageSourceException {

    public InvalidRoleAssignmentException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
