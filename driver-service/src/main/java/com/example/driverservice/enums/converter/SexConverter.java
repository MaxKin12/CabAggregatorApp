package com.example.driverservice.enums.converter;

import com.example.driverservice.enums.Sex;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class SexConverter implements AttributeConverter<Sex, Byte> {
    @Override
    public Byte convertToDatabaseColumn(Sex sex) {
        return Arrays.stream(Sex.values())
                .filter(p -> p.name().equals(sex.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Enum Sex hasn't such attribute: " + sex))
                .getCode();
    }

    @Override
    public Sex convertToEntityAttribute(Byte code) {
        return Arrays.stream(Sex.values())
                .filter(p -> p.getCode() == code)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Enum Sex hasn't such attribute: " + code));
    }
}
