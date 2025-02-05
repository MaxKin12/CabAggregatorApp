package com.example.driverservice.exception;

import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.IllegalEnumArgumentException;
import com.example.driverservice.exception.custom.ResourceNotFoundException;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.INTERNAL_SERVICE_ERROR;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            IllegalEnumArgumentException.class,
            DbModificationAttemptException.class,
            ConstraintViolationException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionHandlerResponse> handleOtherExceptions(Exception e) {
        return new ResponseEntity<>(new ExceptionHandlerResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                getUnknownInternalServerErrorExceptionMessage(e.getMessage())), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getUnknownInternalServerErrorExceptionMessage(String exceptionMessage) {
        return messageSource
                .getMessage(INTERNAL_SERVICE_ERROR, new Object[] {exceptionMessage}, LocaleContextHolder.getLocale());
    }
}
