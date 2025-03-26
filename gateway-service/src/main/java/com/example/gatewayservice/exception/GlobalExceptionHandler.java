package com.example.gatewayservice.exception;

import static com.example.gatewayservice.utility.constants.ExceptionConstants.EXTERNAL_SERVICE_UNAVAILABLE;
import static com.example.gatewayservice.utility.constants.ExceptionConstants.INTERNAL_SERVICE_ERROR;
import static com.example.gatewayservice.utility.constants.ExceptionConstants.SERVICE_PATH_NOT_FOUND;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.support.NotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.resource.NoResourceFoundException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({
        NoResourceFoundException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handlePathNotFoundException(Exception e) {
        return getExceptionResponse(
                new MessageSourceException(SERVICE_PATH_NOT_FOUND, e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler({
        NotFoundException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleServiceUnavailableException(Exception e) {
        return getExceptionResponse(
                new MessageSourceException(EXTERNAL_SERVICE_UNAVAILABLE, e.getMessage()),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

    @ExceptionHandler({
        Exception.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleOtherExceptions(Exception e) {
        return getExceptionResponse(
                new MessageSourceException(INTERNAL_SERVICE_ERROR, e.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    private ResponseEntity<ExceptionHandlerResponse> getExceptionResponse(Exception e, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(
                httpStatus.value(),
                getExceptionMessage(e),
                LocalDateTime.now()),
                httpStatus
        );
    }

    private String getExceptionMessage(Exception e) {
        if (e instanceof MessageSourceException messageSourceException) {
            return messageSource.getMessage(
                    messageSourceException.getMessageKey(),
                    messageSourceException.getArgs(),
                    LocaleContextHolder.getLocale()
            );
        }
        return e.getMessage();
    }

}
