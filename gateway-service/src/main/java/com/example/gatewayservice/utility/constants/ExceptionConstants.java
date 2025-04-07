package com.example.gatewayservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExceptionConstants {

    public static final String INVALID_SERIALISATION_ATTEMPT = "exception.invalid.serialisation.attempt";
    public static final String SERVICE_PATH_NOT_FOUND = "exception.service.not-found";
    public static final String EXTERNAL_SERVICE_UNAVAILABLE = "exception.external-service.unavailable";
    public static final String INTERNAL_SERVICE_ERROR = "exception.internal-service.error";

}
