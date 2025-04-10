package com.example.ridesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessagesTemplate {

    public static final String REQUEST_LOG_TEMPLATE = "Http-{} request | URI: {} | Request args: {}";
    public static final String RESPONSE_LOG_TEMPLATE = "Http-{} response | Status code: {} | URI: {} | Response args: {} | Duration: {} ms";
    public static final String EXCEPTION_RESPONSE_LOG_TEMPLATE = "Http-{} exception response | Status code: {} | URI: {} | Response args: {}";
    public static final String FEIGN_CLIENT_RESPONSE_LOG_TEMPLATE = "FeignClient response | Response: {}";
    public static final String RIDE_PLACED_LOG_TEMPLATE = "Ride placed to RideQueue | QueueRide: {}";
    public static final String RIDE_EXTRACTED_LOG_TEMPLATE = "Ride extracted from RideQueue | QueueRide: {}";
    public static final String TRAVEL_TIME_RESPONSE_LOG_TEMPLATE = "TravelTime API response | Distance: {}";
    public static final String SERIALISATION_DELIMITER = ", ";

}
