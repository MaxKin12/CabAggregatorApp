package com.example.ratesservice.exception;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.INTERNAL_SERVICE_ERROR;

import com.example.ratesservice.client.driver.exception.DriverClientBadRequest;
import com.example.ratesservice.client.driver.exception.DriverNotFoundException;
import com.example.ratesservice.client.passenger.exception.PassengerClientBadRequest;
import com.example.ratesservice.client.passenger.exception.PassengerNotFoundException;
import com.example.ratesservice.client.ride.exception.InvalidRideContentException;
import com.example.ratesservice.client.ride.exception.RidesClientBadRequest;
import com.example.ratesservice.client.ride.exception.RidesNotFoundException;
import com.example.ratesservice.exception.custom.DbModificationAttemptException;
import com.example.ratesservice.exception.custom.IllegalEnumArgumentException;
import com.example.ratesservice.exception.custom.RateAlreadyExistsException;
import com.example.ratesservice.exception.custom.RateListIsEmptyException;
import com.example.ratesservice.exception.custom.RateNotFoundException;
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
        RateNotFoundException.class,
        PassengerNotFoundException.class,
        DriverNotFoundException.class,
        RidesNotFoundException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        IllegalEnumArgumentException.class,
        DbModificationAttemptException.class,
        ConstraintViolationException.class,
        RateAlreadyExistsException.class,
        RateListIsEmptyException.class,
        PassengerClientBadRequest.class,
        DriverClientBadRequest.class,
        RidesClientBadRequest.class,
        InvalidRideContentException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
        Exception.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getUnknownInternalServerErrorExceptionMessage(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getUnknownInternalServerErrorExceptionMessage(String exceptionMessage) {
        return messageSource
                .getMessage(INTERNAL_SERVICE_ERROR, new Object[] {exceptionMessage}, LocaleContextHolder.getLocale());
    }

}
