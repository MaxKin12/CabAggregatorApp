package com.example.driverservice.enums.converter;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ENUM_ARGUMENT;

import com.example.driverservice.enums.UserGender;
import com.example.driverservice.exception.custom.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class UserGenderConverter implements AttributeConverter<UserGender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserGender userGender) {
        return userGender.getCode();
    }

    @Override
    public UserGender convertToEntityAttribute(Integer code) {
        return UserGender.codeToUserGender(code)
                .orElseThrow(() -> new IllegalEnumArgumentException(INVALID_ENUM_ARGUMENT, code.toString()));
    }

}
