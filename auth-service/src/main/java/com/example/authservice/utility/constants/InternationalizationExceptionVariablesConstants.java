package com.example.authservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationExceptionVariablesConstants {

    public static final String INVALID_SERIALISATION_ATTEMPT = "exception.invalid.serialisation.attempt";
    public static final String INTERNAL_SERVER_ERROR = "exception.internal-server.error";
    public static final String FORBIDDEN_ATTEMPT_TO_CREATE_USER = "exception.internal-server.error";
    public static final String FORBIDDEN_ATTEMPT_TO_ASSIGN_ROLE = "exception.internal-server.error";
    public static final String DUPLICATE_USER_DATA = "exception.internal-server.error";
    public static final String INVALID_ATTEMPT_TO_CREATE_USER = "exception.invalid.attempt.change.ride";
    public static final String INVALID_ATTEMPT_TO_ASSIGN_ROLE = "exception.invalid.attempt.change.ride";
    public static final String EXTERNAL_SERVICE_ERROR = "exception.openfeign.external-service.error";
    public static final String SERVICE_UNAVAILABLE = "exception.openfeign.service.unavailable";
    public static final String PASSENGER_SERVICE_IN_OPENED_STATE = "exception.openfeign.passenger-service.opened";
    public static final String DRIVER_SERVICE_IN_OPENED_STATE = "exception.openfeign.driver-service.opened";

}
