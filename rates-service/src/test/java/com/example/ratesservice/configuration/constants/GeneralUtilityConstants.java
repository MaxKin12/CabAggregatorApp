package com.example.ratesservice.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GeneralUtilityConstants {

    public final static String DB_DOCKER_IMAGE = "mysql:latest";
    public final static String CONTROLLER_BASE_URI = "http://localhost/api/v1/rates";
    public final static String GET_PASSENGER_REQUEST_URL = "/passengers/";
    public final static String GET_DRIVER_REQUEST_URL = "/drivers/";
    public final static String GET_RIDES_REQUEST_URL = "/rides/";
    public final static String ENDPOINT_WITH_ID = "/{id}";
    public final static String ENDPOINT_WITH_PASSENGERS_ID = "/passengers/{id}";
    public final static String ENDPOINT_WITH_PASSENGERS = "/passengers";
    public final static String ENDPOINT_WITH_DRIVERS = "/drivers";
    public final static String ID_PARAMETER_NAME = "id";
    public final static String VALUE_PARAMETER_NAME = "value";
    public final static String OFFSET_PARAMETER_NAME = "offset";
    public final static String LIMIT_PARAMETER_NAME = "limit";
    public final static String EXCEPTION_MESSAGE = "Some exception message";
    public final static String KAFKA_TEST_TOPIC = "test-topic";

}
