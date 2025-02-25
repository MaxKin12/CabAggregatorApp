package com.example.ridesservice.client.driver;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.EXTERNAL_SERVICE_ERROR;

import com.example.ridesservice.client.dto.ExternalServiceExceptionHandlerResponse;
import com.example.ridesservice.client.exception.ExternalServiceClientBadRequest;
import com.example.ridesservice.client.exception.ExternalServiceEntityNotFoundException;
import com.example.ridesservice.client.exception.ExternalServiceUnknownInternalServerError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public class ExternalServiceClientDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    private final MessageSource messageSource;

    @Override
    public Exception decode(String methodKey, Response response) {
        ExternalServiceExceptionHandlerResponse exceptionResponse;
        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            exceptionResponse = mapper.readValue(body, ExternalServiceExceptionHandlerResponse.class);
        } catch (IOException e) {
            log.error("Failed attempt to read feign exception response body", e);
            return new ExternalServiceUnknownInternalServerError(e.getMessage());
        }

        String exceptionMessage = getExceptionMessage(exceptionResponse.message());
        log.error("Feign exception: from method - {}; code - {}; message - {}", methodKey,
                exceptionResponse.message(), exceptionResponse.statusCode());

        HttpStatus responseStatue = getResponseStatusOrThrow(response);
        return switch (responseStatue) {
            case BAD_REQUEST -> new ExternalServiceClientBadRequest(exceptionMessage);
            case NOT_FOUND -> new ExternalServiceEntityNotFoundException(exceptionMessage);
            case INTERNAL_SERVER_ERROR -> new ExternalServiceUnknownInternalServerError(exceptionMessage);
            default -> errorDecoder.decode(methodKey, response);
        };
    }

    private HttpStatus getResponseStatusOrThrow(Response response) {
        try {
            return HttpStatus.valueOf(response.status());
        }
        catch (Exception e) {
            log.error("Failed attempt to read feign exception response status", e);
            throw new ExternalServiceUnknownInternalServerError(e.getMessage());
        }
    }

    private String getExceptionMessage(Object... args) {
        return messageSource.getMessage(EXTERNAL_SERVICE_ERROR, args, LocaleContextHolder.getLocale());
    }

}
