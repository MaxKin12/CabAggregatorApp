package com.example.ratesservice.client.ride.decoder;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_SERVICE_ERROR;

import com.example.ratesservice.client.ride.dto.RidesExceptionHandlerResponse;
import com.example.ratesservice.client.ride.exception.RidesClientBadRequest;
import com.example.ratesservice.client.ride.exception.RidesNotFoundException;
import com.example.ratesservice.client.ride.exception.RidesUnknownInternalServerError;
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
public class RidesClientDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    private final MessageSource messageSource;

    @Override
    public Exception decode(String methodKey, Response response) {
        RidesExceptionHandlerResponse passengerExceptionResponse;
        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            passengerExceptionResponse = mapper.readValue(body, RidesExceptionHandlerResponse.class);
        } catch (IOException e) {
            log.error("Failed attempt to read feign exception (RidesClient) response body", e);
            return new RidesUnknownInternalServerError(e.getMessage());
        }

        String exceptionMessage = getExceptionMessage(PASSENGER_SERVICE_ERROR, passengerExceptionResponse.message());
        log.error("Feign exception (RidesClient): from method - {}; code - {}; message - {}", methodKey,
                passengerExceptionResponse.message(), passengerExceptionResponse.statusCode());
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new RidesClientBadRequest(exceptionMessage);
        }
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new RidesNotFoundException(exceptionMessage);
        }
        if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return new RidesUnknownInternalServerError(exceptionMessage);
        }
        return errorDecoder.decode(methodKey, response);
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
