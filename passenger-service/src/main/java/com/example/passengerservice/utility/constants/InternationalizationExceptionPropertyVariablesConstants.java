package com.example.passengerservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationExceptionPropertyVariablesConstants {

    public static final String INVALID_SERIALISATION_ATTEMPT = "invalid.serialisation.attempt";
    public static final String PASSENGER_NOT_FOUND = "exception.passenger.not.found";
    public static final String INVALID_ATTEMPT_CHANGE_PASSENGER = "exception.invalid.attempt.change.passenger";
    public static final String INTERNAL_SERVICE_ERROR = "exception.internal.server.error";
    public static final String CROSS_USER_ACCESS_HEADER_SKIPPED_EXCEPTION = "exception.cross.user.access.header.skipped";
    public static final String CROSS_USER_ACCESS_FORBIDDEN_EXCEPTION = "exception.cross.user.access.forbidden";

}
