package com.example.ridesservice.client.driver.decoder;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_SERVICE_ERROR;

import com.example.ridesservice.client.driver.dto.DriverServiceExceptionHandlerResponse;
import com.example.ridesservice.client.driver.exception.DriverClientBadRequest;
import com.example.ridesservice.client.driver.exception.DriverServiceEntityNotFoundException;
import com.example.ridesservice.client.driver.exception.DriverServiceUnknownInternalServerError;
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
public class DriverClientDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    private final MessageSource messageSource;

    @Override
    public Exception decode(String methodKey, Response response) {
        DriverServiceExceptionHandlerResponse exceptionResponse;
        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            exceptionResponse = mapper.readValue(body, DriverServiceExceptionHandlerResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        String exceptionMessage = getExceptionMessage(DRIVER_SERVICE_ERROR, exceptionResponse.message());
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new DriverClientBadRequest(exceptionMessage);
        }
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new DriverServiceEntityNotFoundException(exceptionMessage);
        }
        if (response.status() == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return new DriverServiceUnknownInternalServerError(
                    getExceptionMessage(exceptionMessage)
            );
        }
        return errorDecoder.decode(methodKey, response);
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
