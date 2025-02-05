package com.example.driverservice.enums.validator;

import com.example.driverservice.enums.annotation.UserGenderValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

public class UserGenderTypeValidator implements ConstraintValidator<UserGenderValidation, String> {
    private List<String> acceptedValues;

    @Override
    public void initialize(UserGenderValidation annotation) {
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
