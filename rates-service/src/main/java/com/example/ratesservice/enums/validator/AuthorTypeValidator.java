package com.example.ratesservice.enums.validator;

import com.example.ratesservice.enums.annotation.AuthorTypeValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Stream;

public class AuthorTypeValidator implements ConstraintValidator<AuthorTypeValidation, String> {

    private List<String> acceptedValues;

    @Override
    public void initialize(AuthorTypeValidation annotation) {
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
