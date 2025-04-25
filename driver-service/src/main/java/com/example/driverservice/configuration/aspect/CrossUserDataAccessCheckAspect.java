package com.example.driverservice.configuration.aspect;

import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.CROSS_USER_ACCESS_FORBIDDEN_EXCEPTION;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.CROSS_USER_ACCESS_HEADER_SKIPPED_EXCEPTION;
import static com.example.driverservice.utility.constants.LogMessagesTemplate.CROSS_USER_DATA_ACCESS_FORBIDDEN;
import static com.example.driverservice.utility.constants.LogMessagesTemplate.CROSS_USER_DATA_ACCESS_HEADER_ID_SKIPPED;
import static com.example.driverservice.utility.constants.LogMessagesTemplate.CROSS_USER_DATA_ACCESS_PATH_ID_SKIPPED;
import static com.example.driverservice.utility.constants.LogMessagesTemplate.CROSS_USER_DATA_ACCESS_PROVIDED;
import static com.example.driverservice.utility.constants.UtilConstants.PASSENGER_ID_PATH_NAME;
import static com.example.driverservice.utility.constants.UtilConstants.USER_ID_HEADER_NAME;

import com.example.driverservice.exception.custom.ResourceSecurityViolationException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

@Component
@Slf4j
@Aspect
public class CrossUserDataAccessCheckAspect {

    @Before("within(com.example.driverservice.controller.DriverController)")
    public void logRequestAndResponse() {
        HttpServletRequest request = getCurrentHttpRequest();
        String pathId = extractIdFromRequest(request);
        if (pathId == null) {
            log.info(CROSS_USER_DATA_ACCESS_PATH_ID_SKIPPED);
            return;
        }
        String headerId = request.getHeader(USER_ID_HEADER_NAME);
        if (headerId == null) {
            log.warn(CROSS_USER_DATA_ACCESS_HEADER_ID_SKIPPED);
            throw new ResourceSecurityViolationException(CROSS_USER_ACCESS_HEADER_SKIPPED_EXCEPTION);
        } else if (!headerId.equals(pathId)) {
            log.warn(CROSS_USER_DATA_ACCESS_FORBIDDEN, headerId, pathId);
            throw new ResourceSecurityViolationException(CROSS_USER_ACCESS_FORBIDDEN_EXCEPTION);
        }
        log.info(CROSS_USER_DATA_ACCESS_PROVIDED, headerId);
    }

    private HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes()))
                .getRequest();
    }

    @SuppressWarnings("unchecked")
    public String extractIdFromRequest(HttpServletRequest request) {
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(
                HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        return pathVariables.get(PASSENGER_ID_PATH_NAME);
    }

}
