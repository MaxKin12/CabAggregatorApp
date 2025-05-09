package com.example.authservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessagesTemplate {

    public static final String REQUEST_LOG_TEMPLATE =
            "Http-{} request | URI: {} | Request body: {}";
    public static final String RESPONSE_LOG_TEMPLATE =
            "Http-{} response | Status code: {} | URI: {} | Response body: {} | Duration: {} ms";
    public static final String EXCEPTION_RESPONSE_LOG_TEMPLATE =
            "Http-{} exception response | Status code: {} | URI: {} | Response body: {}";
    public static final String SERIALISATION_DELIMITER = ", ";

}
