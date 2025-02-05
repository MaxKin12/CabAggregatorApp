package com.example.ridesservice.enums.validator;

import com.example.ridesservice.enums.annotation.StatusValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

public class StatusTypeValidator implements ConstraintValidator<StatusValidation, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(StatusValidation annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .map(String::toLowerCase)
                .toList();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return acceptedValues.contains(value);
    }
}
