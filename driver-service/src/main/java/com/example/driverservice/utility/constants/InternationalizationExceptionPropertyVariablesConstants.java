package com.example.driverservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InternationalizationExceptionPropertyVariablesConstants {

    public static final String INVALID_SERIALISATION_ATTEMPT = "invalid.serialisation.attempt";
    public static final String INVALID_ENUM_ARGUMENT = "exception.invalid.enum.argument";
    public static final String DRIVER_NOT_FOUND = "exception.driver.not.found";
    public static final String CAR_NOT_FOUND = "exception.car.not.found";
    public static final String INVALID_ATTEMPT_CHANGE_DRIVER = "exception.invalid.attempt.change.driver";
    public static final String INVALID_ATTEMPT_CHANGE_CAR = "exception.invalid.attempt.change.car";
    public static final String INTERNAL_SERVICE_ERROR = "exception.internal.server.error";
    public static final String CROSS_USER_ACCESS_HEADER_SKIPPED_EXCEPTION = "exception.cross.user.access.header.skipped";
    public static final String CROSS_USER_ACCESS_FORBIDDEN_EXCEPTION = "exception.cross.user.access.forbidden";

}
