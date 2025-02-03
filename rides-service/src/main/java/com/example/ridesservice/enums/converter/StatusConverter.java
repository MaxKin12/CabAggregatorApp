package com.example.ridesservice.enums.converter;

import com.example.ridesservice.enums.RideStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter
public class StatusConverter implements AttributeConverter<RideStatus, Byte> {
    @Override
    public Byte convertToDatabaseColumn(RideStatus status) {
        return Arrays.stream(RideStatus.values())
                .filter(p -> p.name().equals(status.name()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Enum RideStatus hasn't such attribute: " + status))
                .getCode();
    }

    @Override
    public RideStatus convertToEntityAttribute(Byte code) {
        return Arrays.stream(RideStatus.values())
                .filter(p -> p.getCode() == code)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Enum RideStatus hasn't such attribute: " + code));
    }
}
