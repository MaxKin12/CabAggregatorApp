package com.example.ridesservice.enums.converter;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.exception.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Converter
@RequiredArgsConstructor
public class StatusConverter implements AttributeConverter<RideStatus, Integer> {
    private final MessageSource messageSource;

    @Override
    public Integer convertToDatabaseColumn(RideStatus status) {
        return status.getCode();
    }

    @Override
    public RideStatus convertToEntityAttribute(Integer code) {
        return RideStatus.codeToRideStatus(code)
                .orElseThrow(() -> new IllegalEnumArgumentException(getExceptionMessage(code)));
    }

    private String getExceptionMessage(Integer code) {
        return messageSource
                .getMessage("exception.invalid.enum.argument", new Object[] {code}, LocaleContextHolder.getLocale());
    }
}
