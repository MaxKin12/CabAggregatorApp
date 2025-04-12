package com.example.passengerservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessagesTemplate {

    public static final String REQUEST_LOG_TEMPLATE = "Http-{} request | URI: {} | Request args: {}";
    public static final String RESPONSE_LOG_TEMPLATE =
            "Http-{} response | Status code: {} | URI: {} | Response args: {} | Duration: {} ms";
    public static final String EXCEPTION_RESPONSE_LOG_TEMPLATE =
            "Http-{} exception response | Status code: {} | URI: {} | Response args: {}";
    public static final String KAFKA_LISTENER_EVENT_LOG_TEMPLATE =
            "KafkaListener received event (key={}, partition={}) | Event: {}";
    public static final String SERIALISATION_DELIMITER = ", ";

}
