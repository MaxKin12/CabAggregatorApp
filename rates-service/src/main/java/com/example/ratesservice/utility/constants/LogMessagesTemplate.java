package com.example.ratesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessagesTemplate {

    public static final String REQUEST_LOG_TEMPLATE = "Http-{} request | URI: {} | Request body: {}";
    public static final String RESPONSE_LOG_TEMPLATE =
            "Http-{} response | Status code: {} | URI: {} | Response body: {} | Duration: {} ms";
    public static final String EXCEPTION_RESPONSE_LOG_TEMPLATE =
            "Http-{} exception response | Status code: {} | URI: {} | Response body: {}";
    public static final String FEIGN_CLIENT_REQUEST_LOG_TEMPLATE = "FeignClient request | Request: {}";
    public static final String EVENT_PLACED_LOG_TEMPLATE = "Event placed to EventQueue [{}] | Event: {}";
    public static final String EVENT_EXTRACTED_LOG_TEMPLATE = "Event extracted from EventQueue [{}] | Event: {}";
    public static final String KAFKA_PRODUCER_EVENT_TO_TOPIC_LOG_TEMPLATE =
            "KafkaProducer sent event to topic {} | Event: {}";
    public static final String SERIALISATION_DELIMITER = ", ";

}
