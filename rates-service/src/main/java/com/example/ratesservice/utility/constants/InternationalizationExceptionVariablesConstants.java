package com.example.ratesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationExceptionVariablesConstants {

    public static final String INVALID_ENUM_ARGUMENT = "exception.invalid.enum.argument";

    public static final String RATE_NOT_FOUND = "exception.rate.not.found";

    public static final String RATE_PASSENGER_LIST_IS_EMPTY = "exception.passenger.rate.list.is.empty";

    public static final String RATE_DRIVER_LIST_IS_EMPTY = "exception.driver.rate.list.is.empty";

    public static final String RATE_ALREADY_EXISTS = "exception.rate.already.exists";

    public static final String INVALID_ATTEMPT_CHANGE_RATE = "exception.invalid.attempt.change.rate";

    public static final String INTERNAL_SERVICE_ERROR = "exception.internal.server.error";

    public static final String EXTERNAL_SERVICE_ERROR = "exception.openfeign.external-service.error";

    public static final String INVALID_RIDE_CONTENT = "exception.openfeign.invalid.ride.content.error";

}
