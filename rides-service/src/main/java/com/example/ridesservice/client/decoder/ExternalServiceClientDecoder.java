package com.example.ridesservice.client.decoder;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.EXTERNAL_SERVICE_ERROR;

import com.example.ridesservice.client.dto.ExternalServiceExceptionHandlerResponse;
import com.example.ridesservice.client.exception.ExternalServiceClientBadRequest;
import com.example.ridesservice.client.exception.ExternalServiceEntityNotFoundException;
import com.example.ridesservice.client.exception.ExternalServiceUnknownInternalServerError;
import com.example.ridesservice.utility.validation.FeignClientDecoderValidation;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

@Slf4j
@RequiredArgsConstructor
public class ExternalServiceClientDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    private final FeignClientDecoderValidation validation;

    @Override
    public Exception decode(String methodKey, Response response) {
        ExternalServiceExceptionHandlerResponse exceptionResponse = validation.getExceptionResponse(response);
        log.error("Feign exception: from method - {}; code - {}; message - {}", methodKey,
                exceptionResponse.message(), exceptionResponse.statusCode());

        HttpStatus responseStatue = validation.getResponseStatusOrThrow(response);
        return switch (responseStatue) {
            case BAD_REQUEST ->
                    new ExternalServiceClientBadRequest(EXTERNAL_SERVICE_ERROR, exceptionResponse.message());
            case NOT_FOUND ->
                    new ExternalServiceEntityNotFoundException(EXTERNAL_SERVICE_ERROR, exceptionResponse.message());
            case INTERNAL_SERVER_ERROR ->
                    new ExternalServiceUnknownInternalServerError(EXTERNAL_SERVICE_ERROR, exceptionResponse.message());
            default -> errorDecoder.decode(methodKey, response);
        };
    }

}
