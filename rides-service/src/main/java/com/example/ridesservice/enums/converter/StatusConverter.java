package com.example.ridesservice.enums.converter;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ENUM_ARGUMENT;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.exception.custom.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class StatusConverter implements AttributeConverter<RideStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(@NonNull RideStatus status) {
        return status.getCode();
    }

    @Override
    public RideStatus convertToEntityAttribute(@NonNull Integer code) {
        return RideStatus.codeToRideStatus(code)
                .orElseThrow(() -> new IllegalEnumArgumentException(INVALID_ENUM_ARGUMENT, code.toString()));
    }

}
