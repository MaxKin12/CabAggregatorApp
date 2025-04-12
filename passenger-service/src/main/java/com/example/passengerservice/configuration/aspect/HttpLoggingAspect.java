package com.example.passengerservice.configuration.aspect;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_SERIALISATION_ATTEMPT;
import static com.example.passengerservice.utility.constants.LogMessagesTemplate.EXCEPTION_RESPONSE_LOG_TEMPLATE;
import static com.example.passengerservice.utility.constants.LogMessagesTemplate.REQUEST_LOG_TEMPLATE;
import static com.example.passengerservice.utility.constants.LogMessagesTemplate.RESPONSE_LOG_TEMPLATE;
import static com.example.passengerservice.utility.constants.LogMessagesTemplate.SERIALISATION_DELIMITER;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Slf4j
@Aspect
@RequiredArgsConstructor
public class HttpLoggingAspect {

    private final ObjectMapper jsonMapper;

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getCurrentHttpRequest();
        String requestBody = extractRequestBody(joinPoint);
        log.info(REQUEST_LOG_TEMPLATE,
                request.getMethod(),
                request.getRequestURI(),
                requestBody);

        long startTime = System.currentTimeMillis();
        ResponseEntity<?> response = (ResponseEntity<?>) joinPoint.proceed();
        long duration = System.currentTimeMillis() - startTime;

        String responseBody = serializeToJson(response);
        log.info(RESPONSE_LOG_TEMPLATE,
                request.getMethod(),
                response.getStatusCode(),
                request.getRequestURI(),
                responseBody,
                duration);
        return response;
    }

    @AfterReturning(
            pointcut = "@within(org.springframework.web.bind.annotation.RestControllerAdvice))",
            returning = "result"
    )
    public Object logRequestAndResponseForExceptionHandler(
            Object result
    ) {
        HttpServletRequest httpRequest = getCurrentHttpRequest();
        ResponseEntity<?> response = (ResponseEntity<?>) result;
        String responseBody = serializeToJson(response);
        log.info(EXCEPTION_RESPONSE_LOG_TEMPLATE,
                httpRequest.getMethod(),
                response.getStatusCode(),
                httpRequest.getRequestURI(),
                responseBody);
        return response;
    }

    private HttpServletRequest getCurrentHttpRequest() {
        return ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                .getRequestAttributes()))
                .getRequest();
    }

    private String extractRequestBody(ProceedingJoinPoint joinPoint) {
        return Arrays.stream(joinPoint.getArgs())
                .map(this::serializeToJson)
                .collect(Collectors.joining(SERIALISATION_DELIMITER));
    }

    private String serializeToJson(Object object) {
        if (object == null) {
            return "";
        }
        try {
            return jsonMapper.writeValueAsString(object);
        } catch (Exception e) {
            log.error(INVALID_SERIALISATION_ATTEMPT, e);
            return INVALID_SERIALISATION_ATTEMPT;
        }
    }

}
