package com.example.ridesservice.exception.custom;

public class RideNotFoundException extends RuntimeException {

    public RideNotFoundException(String message) {
        super(message);
    }

}
