package com.example.ridesservice.exception.custom;

public class WrongStatusChangeException extends RuntimeException {

    public WrongStatusChangeException(String message) {
        super(message);
    }

}
