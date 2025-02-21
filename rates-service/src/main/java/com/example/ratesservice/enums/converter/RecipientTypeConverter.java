package com.example.ratesservice.enums.converter;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ENUM_ARGUMENT;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.exception.custom.IllegalEnumArgumentException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

@Converter
@RequiredArgsConstructor
public class RecipientTypeConverter implements AttributeConverter<RecipientType, Integer> {

    private final MessageSource messageSource;

    @Override
    public Integer convertToDatabaseColumn(@NonNull RecipientType status) {
        return status.getCode();
    }

    @Override
    public RecipientType convertToEntityAttribute(@NonNull Integer code) {
        return RecipientType.codeToRideStatus(code)
                .orElseThrow(() -> new IllegalEnumArgumentException(getExceptionMessage(code)));
    }

    private String getExceptionMessage(Integer code) {
        return messageSource
                .getMessage(INVALID_ENUM_ARGUMENT, new Object[] {code}, LocaleContextHolder.getLocale());
    }

}
