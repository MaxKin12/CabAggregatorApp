package com.example.ridesservice.enums.converter;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.exception.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;

@Converter
@RequiredArgsConstructor
public class StatusConverter implements AttributeConverter<RideStatus, Byte> {
    private final MessageSource messageSource;

    @Override
    public Byte convertToDatabaseColumn(RideStatus status) {
        return Arrays.stream(RideStatus.values())
                .filter(p -> p.name().equals(status.name()))
                .findAny()
                .orElseThrow(() -> new IllegalEnumArgumentException(messageSource
                        .getMessage("exception.invalid.enum.argument", new Object[] {status},
                                LocaleContextHolder.getLocale())))
                .getCode();
    }

    @Override
    public RideStatus convertToEntityAttribute(Byte code) {
        return Arrays.stream(RideStatus.values())
                .filter(p -> p.getCode() == code)
                .findAny()
                .orElseThrow(() -> new IllegalEnumArgumentException(messageSource
                        .getMessage("exception.invalid.enum.argument", new Object[] {code},
                                LocaleContextHolder.getLocale())));
    }
}
