package com.example.authservice.client.decoder;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.EXTERNAL_SERVICE_ERROR;
import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.SERVICE_UNAVAILABLE;

import com.example.authservice.dto.exception.ExternalServiceExceptionHandlerResponse;
import com.example.authservice.exception.external.ExternalServiceClientBadRequest;
import com.example.authservice.exception.external.ExternalServiceEntityNotFoundException;
import com.example.authservice.exception.external.ExternalServiceUnknownInternalServerError;
import com.example.authservice.exception.custom.FeignClientTemporarilyUnavailable;
import com.example.authservice.utility.validation.FeignClientDecoderValidation;
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
        if (exceptionResponse != null) {
            log.error("Feign exception: from method - {}; code - {}; message - {}; time = {}", methodKey,
                    exceptionResponse.message(), exceptionResponse.statusCode(), exceptionResponse.localDateTime());
        }

        HttpStatus responseStatue = validation.getResponseStatusOrThrow(response);
        return switch (responseStatue) {
            case BAD_REQUEST ->
                    new ExternalServiceClientBadRequest(EXTERNAL_SERVICE_ERROR,
                            exceptionResponse != null ? exceptionResponse.message() : "");
            case NOT_FOUND ->
                    new ExternalServiceEntityNotFoundException(EXTERNAL_SERVICE_ERROR,
                            exceptionResponse != null ? exceptionResponse.message() : "");
            case SERVICE_UNAVAILABLE ->
                    new FeignClientTemporarilyUnavailable(SERVICE_UNAVAILABLE);
            case INTERNAL_SERVER_ERROR ->
                    new ExternalServiceUnknownInternalServerError(EXTERNAL_SERVICE_ERROR,
                            exceptionResponse != null ? exceptionResponse.message() : "");
            default -> errorDecoder.decode(methodKey, response);
        };
    }

}
