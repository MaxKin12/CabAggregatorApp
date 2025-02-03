package com.example.driverservice.enums.converter;

import com.example.driverservice.enums.Sex;
import com.example.driverservice.exception.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Arrays;

@Converter
@RequiredArgsConstructor
public class SexConverter implements AttributeConverter<Sex, Byte> {
    private final MessageSource messageSource;

    @Override
    public Byte convertToDatabaseColumn(Sex sex) {
        return Arrays.stream(Sex.values())
                .filter(p -> p.name().equals(sex.name()))
                .findAny()
                .orElseThrow(() -> new IllegalEnumArgumentException(messageSource
                        .getMessage("exception.invalid.enum.argument", new Object[] {sex},
                                LocaleContextHolder.getLocale())))
                .getCode();
    }

    @Override
    public Sex convertToEntityAttribute(Byte code) {
        return Arrays.stream(Sex.values())
                .filter(p -> p.getCode() == code)
                .findAny()
                .orElseThrow(() -> new IllegalEnumArgumentException(messageSource
                        .getMessage("exception.invalid.enum.argument", new Object[] {code},
                                LocaleContextHolder.getLocale())));
    }
}
