package com.example.ridesservice.exception;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INTERNAL_SERVICE_ERROR;

import com.example.ridesservice.client.exception.DriverNotContainsCarException;
import com.example.ridesservice.client.exception.ExternalServiceClientBadRequest;
import com.example.ridesservice.client.exception.ExternalServiceEntityNotFoundException;
import com.example.ridesservice.exception.custom.DbModificationAttemptException;
import com.example.ridesservice.exception.custom.IllegalEnumArgumentException;
import com.example.ridesservice.exception.custom.RideNotContainsDriverException;
import com.example.ridesservice.exception.custom.RideNotFoundException;
import com.example.ridesservice.exception.custom.TimetravelRequestException;
import com.example.ridesservice.exception.custom.WrongStatusChangeException;
import jakarta.validation.ConstraintViolationException;
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
    public ResponseEntity<RideExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new RideExceptionHandlerResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        TimetravelRequestException.class,
        IllegalEnumArgumentException.class,
        DbModificationAttemptException.class,
        ConstraintViolationException.class,
        ExternalServiceClientBadRequest.class,
        DriverNotContainsCarException.class,
        RideNotContainsDriverException.class,
        WrongStatusChangeException.class
    })
    public ResponseEntity<RideExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(new RideExceptionHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        Exception.class
    })
    public ResponseEntity<RideExceptionHandlerResponse> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new RideExceptionHandlerResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getUnknownInternalServerErrorExceptionMessage(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getUnknownInternalServerErrorExceptionMessage(String exceptionMessage) {
        return messageSource
                .getMessage(INTERNAL_SERVICE_ERROR, new Object[] {exceptionMessage}, LocaleContextHolder.getLocale());
    }

}
