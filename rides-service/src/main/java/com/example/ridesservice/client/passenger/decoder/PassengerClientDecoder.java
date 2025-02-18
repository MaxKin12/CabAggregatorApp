package com.example.ridesservice.client.passenger.decoder;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_SERVICE_ERROR;

import com.example.ridesservice.client.passenger.dto.PassengerExceptionHandlerResponse;
import com.example.ridesservice.client.passenger.exception.PassengerClientBadRequest;
import com.example.ridesservice.client.passenger.exception.PassengerNotFoundException;
import com.example.ridesservice.client.passenger.exception.PassengerUnknownInternalServerError;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;

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
            return new PassengerUnknownInternalServerError(e.getMessage());
        }

        String exceptionMessage = getExceptionMessage(PASSENGER_SERVICE_ERROR, passengerExceptionResponse.message());
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
