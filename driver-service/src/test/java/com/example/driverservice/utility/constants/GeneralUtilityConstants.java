package com.example.driverservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneralUtilityConstants {

    public final static String EXCEPTION_MESSAGE = "Some exception message";
    public final static String ATTEMPT_CHANGE_CREATE = "create";
    public final static String ATTEMPT_CHANGE_UPDATE = "update";
    public final static String ID_PARAMETER_NAME = "id";
    public final static String OFFSET_PARAMETER_NAME = "offset";
    public final static String LIMIT_PARAMETER_NAME = "limit";
    public final static String DRIVER_CONTROLLER_BASE_URI = "http://localhost/api/v1/drivers";
    public final static String CAR_CONTROLLER_BASE_URI = "http://localhost/api/v1/cars";
    public final static String DRIVER_CONTROLLER_BASE_URI_DETERMINED = "http://localhost:8082/api/v1/drivers";
    public final static String CAR_CONTROLLER_BASE_URI_DETERMINED = "http://localhost:8082/api/v1/cars";
    public final static String ENDPOINT_WITH_ID = "/{id}";

}
