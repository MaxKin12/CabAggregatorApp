package com.example.gatewayservice.configuration.aspect;

import static com.example.gatewayservice.utility.constants.ExceptionConstants.INVALID_SERIALISATION_ATTEMPT;
import static com.example.gatewayservice.utility.constants.LogMessagesTemplate.EXCEPTION_RESPONSE_LOG_TEMPLATE;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
@RequiredArgsConstructor
public class HttpLoggingAspect {

    private final ObjectMapper jsonMapper;

    @AfterReturning(
            pointcut = "@within(org.springframework.web.bind.annotation.RestControllerAdvice))",
            returning = "result"
    )
    public Object logRequestAndResponseForExceptionHandler(
            Object result
    ) {
        ResponseEntity<?> response = (ResponseEntity<?>) result;
        String responseBody = serializeToJson(response);
        log.info(EXCEPTION_RESPONSE_LOG_TEMPLATE,
                response.getStatusCode(),
                responseBody);
        return response;
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
