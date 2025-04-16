package com.example.authservice.utility.validation;

import com.example.authservice.dto.exception.ExternalServiceExceptionHandlerResponse;
import feign.Response;
import org.springframework.http.HttpStatus;

public interface FeignClientDecoderValidation {

    ExternalServiceExceptionHandlerResponse getExceptionResponse(Response response);

    HttpStatus getResponseStatusOrThrow(Response response);

}
