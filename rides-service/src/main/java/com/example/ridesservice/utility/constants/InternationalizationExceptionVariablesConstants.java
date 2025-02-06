package com.example.ridesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationExceptionVariablesConstants {

    public static final String INVALID_ENUM_ARGUMENT = "exception.invalid.enum.argument";

    public static final String RIDE_NOT_FOUND = "exception.ride.not.found";

    public static final String INVALID_ATTEMPT_CHANGE_RIDE = "exception.invalid.attempt.change.ride";

    public static final String TIMETRAVEL_REQUEST_EXCEPTION = "exception.timetravel.request";

    public static final String ADDRESS_NOT_FOUND = "exception.address.not.found";

    public static final String INTERNAL_SERVICE_ERROR = "exception.internal.server.error";

}
