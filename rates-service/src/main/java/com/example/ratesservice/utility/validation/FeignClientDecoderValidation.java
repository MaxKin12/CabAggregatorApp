package com.example.ratesservice.utility.validation;

import com.example.ratesservice.client.dto.ExternalServiceExceptionHandlerResponse;
import feign.Response;
import org.springframework.http.HttpStatus;

public interface FeignClientDecoderValidation {

    ExternalServiceExceptionHandlerResponse getExceptionResponse(Response response);

    HttpStatus getResponseStatusOrThrow(Response response);

}
