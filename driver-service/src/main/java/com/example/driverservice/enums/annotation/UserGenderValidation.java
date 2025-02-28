package com.example.driverservice.enums.annotation;

import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.GENDER_INVALID_PATTERN;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.driverservice.enums.validator.UserGenderTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = UserGenderTypeValidator.class)
public @interface UserGenderValidation {

    Class<? extends Enum<?>> enumClass();

    String message() default GENDER_INVALID_PATTERN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
