package com.example.ridesservice.enums.annotation;

import com.example.ridesservice.enums.validator.StatusTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = StatusTypeValidator.class)
public @interface StatusValidation {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid status entered";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
