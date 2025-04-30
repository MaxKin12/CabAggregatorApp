package com.example.ridesservice.exception;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INTERNAL_SERVER_ERROR;

import com.example.ridesservice.exception.external.DriverNotContainsCarException;
import com.example.ridesservice.exception.external.ExternalServiceClientBadRequest;
import com.example.ridesservice.exception.external.ExternalServiceEntityNotFoundException;
import com.example.ridesservice.dto.exception.ExceptionHandlerResponse;
import com.example.ridesservice.exception.custom.DbModificationAttemptException;
import com.example.ridesservice.exception.custom.FeignClientTemporarilyUnavailable;
import com.example.ridesservice.exception.custom.IllegalEnumArgumentException;
import com.example.ridesservice.exception.custom.RideNotContainsDriverException;
import com.example.ridesservice.exception.custom.RideNotFoundException;
import com.example.ridesservice.exception.custom.TimetravelRequestException;
import com.example.ridesservice.exception.custom.WrongStatusTransitionException;
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
        RideNotFoundException.class,
        ExternalServiceEntityNotFoundException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return getExceptionResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        TimetravelRequestException.class,
        IllegalEnumArgumentException.class,
        DbModificationAttemptException.class,
        ConstraintViolationException.class,
        ExternalServiceClientBadRequest.class,
        DriverNotContainsCarException.class,
        RideNotContainsDriverException.class,
        WrongStatusTransitionException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return getExceptionResponse(e, HttpStatus.BAD_REQUEST);
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
