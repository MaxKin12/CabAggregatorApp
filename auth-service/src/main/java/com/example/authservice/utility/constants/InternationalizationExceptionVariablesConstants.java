package com.example.authservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationExceptionVariablesConstants {

    public static final String INVALID_SERIALISATION_ATTEMPT = "exception.invalid.serialisation.attempt";
    public static final String INTERNAL_SERVER_ERROR = "exception.internal-server.error";
    public static final String RIDE_NOT_FOUND = "exception.ride.not.found";
    public static final String INVALID_ATTEMPT_CHANGE_RIDE = "exception.invalid.attempt.change.ride";
    public static final String INVALID_ENUM_ARGUMENT = "exception.invalid.enum.argument";
    public static final String WRONG_STATUS_TRANSITION = "exception.status.wrong.transition";
    public static final String QUEUE_IS_EMPTY = "exception.queue.is.empty";
    public static final String QUEUE_INVALID_RIDE_SAVE = "exception.queue.invalid.ride.save";
    public static final String TIMETRAVEL_REQUEST_EXCEPTION = "exception.timetravel.request.exception";
    public static final String ADDRESS_NOT_FOUND = "exception.timetravel.address.not.found";
    public static final String ADDRESS_TOO_FAR = "exception.timetravel.address.too.far";
    public static final String DRIVER_NOT_CONTAINS_CAR = "exception.openfeign.driver.not.contains.car";
    public static final String EXTERNAL_SERVICE_ERROR = "exception.openfeign.external-service.error";
    public static final String SERVICE_UNAVAILABLE = "exception.openfeign.service.unavailable";
    public static final String PASSENGER_SERVICE_IN_OPENED_STATE = "exception.openfeign.passenger-service.opened";
    public static final String DRIVER_SERVICE_IN_OPENED_STATE = "exception.openfeign.driver-service.opened";

}
