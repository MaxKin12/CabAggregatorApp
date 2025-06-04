package com.example.passengerservice.utility.constants;

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
    public static final String KAFKA_LISTENER_EVENT_LOG_TEMPLATE =
            "KafkaListener received event (key={}, partition={}) | Event: {}";
    public static final String CROSS_USER_DATA_ACCESS_PATH_ID_SKIPPED =
            "Cross user data change attempt: path id absences, check skipped.";
    public static final String CROSS_USER_DATA_ACCESS_HEADER_ID_SKIPPED =
            "Cross user data change attempt: header X-User-Id absences in request.";
    public static final String CROSS_USER_DATA_ACCESS_FORBIDDEN =
            "Cross user data access attempt: access forbidden, user ({}) tried to get access to user ({})";
    public static final String CROSS_USER_DATA_ACCESS_PROVIDED =
            "Cross user data access attempt: access provided to user ({}).";

}
