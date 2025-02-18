package com.example.ridesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationExceptionVariablesConstants {

    public static final String INVALID_ENUM_ARGUMENT = "exception.invalid.enum.argument";

    public static final String RIDE_NOT_FOUND = "exception.ride.not.found";

    public static final String INVALID_ATTEMPT_CHANGE_RIDE = "exception.invalid.attempt.change.ride";

    public static final String WRONG_STATUS_CHANGE = "exception.status.wrong.change";

    public static final String TIMETRAVEL_REQUEST_EXCEPTION = "exception.timetravel.request";

    public static final String ADDRESS_NOT_FOUND = "exception.address.not.found";

    public static final String ADDRESS_TOO_FAR = "exception.address.too.far";

    public static final String INTERNAL_SERVICE_ERROR = "exception.internal.server.error";

    public static final String RIDE_CONTAINS_DRIVER = "exception.ride.contains.driver";

    public static final String DRIVER_NOT_CONTAINS_CAR = "exception.openfeign.driver.not.contains.car";

    public static final String PASSENGER_SERVICE_ERROR = "exception.openfeign.passenger-service.error";

    public static final String DRIVER_SERVICE_ERROR = "exception.openfeign.driver-service.error";

}
