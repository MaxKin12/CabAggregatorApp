package com.example.driverservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionMessagesConstants {
    public static final String NEGATIVE_ID_MESSAGE = "Id must be a positive number";
    public static final String DRIVER_NOT_FOUND_MESSAGE = "Driver with id (%d) not found";
    public static final String INVALID_ATTEMPT_MESSAGE = "Invalid attempt to %s driver: %s";
}
