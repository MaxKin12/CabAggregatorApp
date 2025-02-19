package com.example.passengerservice.exception;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.INTERNAL_SERVICE_ERROR;

import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
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
        PassengerNotFoundException.class
    })
    public ResponseEntity<PassengerExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new PassengerExceptionHandlerResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        DbModificationAttemptException.class,
        ConstraintViolationException.class
    })
    public ResponseEntity<PassengerExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(new PassengerExceptionHandlerResponse(
                HttpStatus.BAD_REQUEST.value(), e.getMessage()), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler({
        Exception.class
    })
    public ResponseEntity<PassengerExceptionHandlerResponse> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new PassengerExceptionHandlerResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getUnknownInternalServerErrorExceptionMessage(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getUnknownInternalServerErrorExceptionMessage(String exceptionMessage) {
        return messageSource
                .getMessage(INTERNAL_SERVICE_ERROR, new Object[] {exceptionMessage}, LocaleContextHolder.getLocale());
    }

}
