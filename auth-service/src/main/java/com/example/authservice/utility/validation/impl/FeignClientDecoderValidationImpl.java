package com.example.authservice.utility.validation.impl;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.EXTERNAL_SERVICE_ERROR;

import com.example.authservice.dto.exception.ExternalServiceExceptionHandlerResponse;
import com.example.authservice.exception.external.ExternalServiceUnknownInternalServerError;
import com.example.authservice.utility.validation.FeignClientDecoderValidation;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import java.io.InputStream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FeignClientDecoderValidationImpl implements FeignClientDecoderValidation {

    @Override
    public ExternalServiceExceptionHandlerResponse getExceptionResponse(Response response) {
        try (InputStream body = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.findAndRegisterModules();
            return mapper.readValue(body, ExternalServiceExceptionHandlerResponse.class);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public HttpStatus getResponseStatusOrThrow(Response response) {
        try {
            return HttpStatus.valueOf(response.status());
        } catch (Exception e) {
            log.error("Failed attempt to read feign exception response status", e);
            throw new ExternalServiceUnknownInternalServerError(EXTERNAL_SERVICE_ERROR, e.getMessage());
        }
    }

}
