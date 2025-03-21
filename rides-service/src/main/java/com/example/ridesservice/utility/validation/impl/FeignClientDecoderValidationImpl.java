package com.example.ridesservice.utility.validation.impl;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.EXTERNAL_SERVICE_ERROR;

import com.example.ridesservice.client.dto.ExternalServiceExceptionHandlerResponse;
import com.example.ridesservice.client.exception.ExternalServiceUnknownInternalServerError;
import com.example.ridesservice.utility.validation.FeignClientDecoderValidation;
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
