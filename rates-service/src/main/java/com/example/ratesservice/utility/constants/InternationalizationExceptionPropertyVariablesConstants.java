package com.example.ratesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationExceptionPropertyVariablesConstants {

    public static final String INVALID_SERIALISATION_ATTEMPT = "invalid.serialisation.attempt";
    public static final String RATE_NOT_FOUND = "exception.rate.not.found";
    public static final String PASSENGER_RATE_LIST_IS_EMPTY = "exception.passenger.rate.list.is.empty";
    public static final String DRIVER_RATE_LIST_IS_EMPTY = "exception.driver.rate.list.is.empty";
    public static final String RATE_ALREADY_EXISTS = "exception.rate.already.exists";
    public static final String INVALID_ATTEMPT_CHANGE_RATE = "exception.invalid.attempt.change.rate";
    public static final String INVALID_ENUM_ARGUMENT = "exception.invalid.enum.argument";
    public static final String INTERNAL_SERVICE_ERROR = "exception.internal.service.error";
    public static final String EXTERNAL_SERVICE_ERROR = "exception.openfeign.external-service.error";
    public static final String INVALID_RIDE_CONTENT = "exception.openfeign.invalid.ride.content";
    public static final String INVALID_RIDE_STATUS = "exception.openfeign.invalid.ride.status";
    public static final String SERVICE_UNAVAILABLE = "exception.openfeign.service.unavailable";
    public static final String PASSENGER_SERVICE_IN_OPENED_STATE = "exception.openfeign.passenger-service.opened";
    public static final String DRIVER_SERVICE_IN_OPENED_STATE = "exception.openfeign.driver-service.opened";
    public static final String RIDES_SERVICE_IN_OPENED_STATE = "exception.openfeign.rides-service.opened";
    public static final String KAFKA_INVALID_SEND = "exception.kafka.invalid.send";

}
