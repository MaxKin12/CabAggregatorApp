package com.example.ridesservice.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneralUtilityConstants {

    public final static String DB_DOCKER_IMAGE = "mysql:latest";
    public final static String CONTROLLER_BASE_URI = "http://localhost/api/v1/rides";
    public final static String GET_PASSENGER_REQUEST_URL = "/";
    public final static String GET_DRIVER_REQUEST_URL = "/drivers/";
    public final static String GET_CAR_REQUEST_URL = "/cars/";
    public final static String ENDPOINT_WITH_ID = "/{id}";
    public final static String ID_PARAMETER_NAME = "id";
    public final static String RIDE_LIST_PARAMETER_NAME = "rideList";
    public final static String PRICE_PARAMETER_NAME = "price";
    public final static String ORDER_TIME_PARAMETER_NAME = "orderTime";
    public final static String OFFSET_PARAMETER_NAME = "offset";
    public final static String LIMIT_PARAMETER_NAME = "limit";
    public final static String ATTEMPT_CHANGE_CREATE = "create";
    public final static String ATTEMPT_CHANGE_UPDATE = "update";
    public final static String EXCEPTION_MESSAGE_KEY_FIELD = "messageKey";
    public final static String EXCEPTION_MESSAGE = "Some exception message";

}
