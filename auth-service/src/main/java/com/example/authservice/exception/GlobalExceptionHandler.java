package com.example.authservice.exception;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.INTERNAL_SERVER_ERROR;

import com.example.authservice.exception.custom.DuplicateUsersException;
import com.example.authservice.exception.custom.ForbiddenAccessException;
import com.example.authservice.exception.external.DriverNotContainsCarException;
import com.example.authservice.exception.external.ExternalServiceClientBadRequest;
import com.example.authservice.exception.external.ExternalServiceEntityNotFoundException;
import com.example.authservice.dto.exception.ExceptionHandlerResponse;
import com.example.authservice.exception.custom.FeignClientTemporarilyUnavailable;
import com.sun.jdi.request.DuplicateRequestException;
import jakarta.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler({
        ExternalServiceEntityNotFoundException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return getExceptionResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        ConstraintViolationException.class,
        ExternalServiceClientBadRequest.class,
        DriverNotContainsCarException.class,
        DuplicateUsersException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return getExceptionResponse(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        ForbiddenAccessException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleForbiddenRequestException(Exception e) {
        return getExceptionResponse(e, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
        FeignClientTemporarilyUnavailable.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleUnavailableServiceExceptions(Exception e) {
        return getExceptionResponse(e, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler({
        Exception.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleOtherExceptions(Exception e) {
        e.printStackTrace();
        return getExceptionResponse(
                new MessageSourceException(INTERNAL_SERVER_ERROR, getExceptionMessage(e)),
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
