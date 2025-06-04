package com.example.gatewayservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessagesTemplate {

    public static final String EXCEPTION_RESPONSE_LOG_TEMPLATE =
            "Gateway exception response | Status code: {} | Response body: {}";
    public static final String USER_ID_HEADER_ADDED =
            "Add user id header GlobalFilter: header added with value ({})";
    public static final String USER_ID_HEADER_SKIPPED =
            "Add user id header GlobalFilter: header not added";

}
