package com.example.driverservice.exception;

import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INTERNAL_SERVICE_ERROR;

import com.example.driverservice.dto.exception.ExceptionHandlerResponse;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.exception.custom.IllegalEnumArgumentException;
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
        EntityNotFoundException.class
    })
    public ResponseEntity<ExceptionHandlerResponse> handleResourceNotFoundException(Exception e) {
        return getExceptionResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
        IllegalEnumArgumentException.class,
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
