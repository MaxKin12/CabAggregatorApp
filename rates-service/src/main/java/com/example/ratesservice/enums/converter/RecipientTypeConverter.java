package com.example.ratesservice.enums.converter;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ENUM_ARGUMENT;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.exception.custom.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class RecipientTypeConverter implements AttributeConverter<RecipientType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(@NonNull RecipientType status) {
        return status.getCode();
    }

    @Override
    public RecipientType convertToEntityAttribute(@NonNull Integer code) {
        return RecipientType.codeToRideStatus(code)
                .orElseThrow(() -> new IllegalEnumArgumentException(INVALID_ENUM_ARGUMENT, code.toString()));
    }

}
