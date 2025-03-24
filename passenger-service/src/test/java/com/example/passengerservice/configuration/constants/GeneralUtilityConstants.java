package com.example.passengerservice.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneralUtilityConstants {

    public final static String DB_DOCKER_IMAGE = "mysql:latest";
    public final static String CONTROLLER_BASE_URI = "http://localhost/api/v1/passengers";
    public final static String ENDPOINT_WITH_ID = "/{id}";
    public final static String ID_PARAMETER_NAME = "id";
    public final static String ATTEMPT_CHANGE_CREATE = "create";
    public final static String ATTEMPT_CHANGE_UPDATE = "update";
    public final static String OFFSET_PARAMETER_NAME = "offset";
    public final static String LIMIT_PARAMETER_NAME = "limit";
    public final static String EXCEPTION_MESSAGE = "Some exception message";

}
