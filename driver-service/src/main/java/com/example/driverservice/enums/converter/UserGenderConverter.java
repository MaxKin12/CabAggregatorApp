package com.example.driverservice.enums.converter;

import com.example.driverservice.enums.UserGender;
import com.example.driverservice.exception.custom.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ENUM_ARGUMENT;

@Converter
@RequiredArgsConstructor
public class UserGenderConverter implements AttributeConverter<UserGender, Integer> {
    private final MessageSource messageSource;

    @Override
    public Integer convertToDatabaseColumn(UserGender userGender) {
        return userGender.getCode();
    }

    @Override
    public UserGender convertToEntityAttribute(Integer code) {
        return UserGender.codeToUserGender(code)
                .orElseThrow(() -> new IllegalEnumArgumentException(getExceptionMessage(code)));
    }

    private String getExceptionMessage(Integer code) {
        return messageSource
                .getMessage(INVALID_ENUM_ARGUMENT, new Object[] {code}, LocaleContextHolder.getLocale());
    }
}
