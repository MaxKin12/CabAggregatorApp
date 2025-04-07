package com.example.gatewayservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LogMessagesTemplate {

    public static final String EXCEPTION_RESPONSE_LOG_TEMPLATE = "Gateway exception response | Status code: {} | Response args: {}";

}
