package com.example.ridesservice.exception.custom;

public class RideNotContainsDriverException extends RuntimeException {

    public RideNotContainsDriverException(String message) {
        super(message);
    }

}
