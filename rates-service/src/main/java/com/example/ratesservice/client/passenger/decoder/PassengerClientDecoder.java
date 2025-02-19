package com.example.ratesservice.client.passenger.decoder;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_SERVICE_ERROR;

import com.example.ratesservice.client.passenger.dto.PassengerExceptionHandlerResponse;
import com.example.ratesservice.client.passenger.exception.PassengerClientBadRequest;
import com.example.ratesservice.client.passenger.exception.PassengerNotFoundException;
import com.example.ratesservice.client.passenger.exception.PassengerUnknownInternalServerError;
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
public class PassengerClientDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    private final MessageSource messageSource;

    @Override
    public Exception decode(String methodKey, Response response) {
        PassengerExceptionHandlerResponse passengerExceptionResponse;
        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            passengerExceptionResponse = mapper.readValue(body, PassengerExceptionHandlerResponse.class);
        } catch (IOException e) {
            log.error("Failed attempt to read feign exception (PassengerClient) response body", e);
            return new PassengerUnknownInternalServerError(e.getMessage());
        }

        String exceptionMessage = getExceptionMessage(PASSENGER_SERVICE_ERROR, passengerExceptionResponse.message());
        log.error("Feign exception (PassengerClient): from method - {}; code - {}; message - {}", methodKey,
                passengerExceptionResponse.message(), passengerExceptionResponse.statusCode());
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new PassengerClientBadRequest(exceptionMessage);
        }
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new PassengerNotFoundException(exceptionMessage);
        }
        if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return new PassengerUnknownInternalServerError(exceptionMessage);
        }
        return errorDecoder.decode(methodKey, response);
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
