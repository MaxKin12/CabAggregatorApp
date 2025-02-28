package com.example.ridesservice.utility.validation;

import com.example.ridesservice.client.dto.ExternalServiceExceptionHandlerResponse;
import feign.Response;
import org.springframework.http.HttpStatus;

public interface FeignClientDecoderValidation {

    ExternalServiceExceptionHandlerResponse getExceptionResponse(Response response);

    HttpStatus getResponseStatusOrThrow(Response response);

}
