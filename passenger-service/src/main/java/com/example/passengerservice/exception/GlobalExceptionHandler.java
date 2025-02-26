package com.example.passengerservice.exception;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INTERNAL_SERVICE_ERROR;

import com.example.passengerservice.dto.exception.ExceptionHandlerResponse;
import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
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
        PassengerNotFoundException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return getExceptionResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        DbModificationAttemptException.class,
        ConstraintViolationException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleBadRequestException(Exception e) {
        return getExceptionResponse(e, HttpStatus.BAD_REQUEST);
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
