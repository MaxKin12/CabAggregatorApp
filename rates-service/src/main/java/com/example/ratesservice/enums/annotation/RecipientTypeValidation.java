package com.example.ratesservice.enums.annotation;

import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.RECIPIENT_INVALID_PATTERN;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import com.example.ratesservice.enums.validator.RecipientTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = RecipientTypeValidator.class)
public @interface RecipientTypeValidation {

    Class<? extends Enum<?>> enumClass();

    String message() default RECIPIENT_INVALID_PATTERN;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
