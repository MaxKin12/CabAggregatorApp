package com.example.driverservice.enums.annotation;

import com.example.driverservice.enums.validator.SexTypeValidator;
import jakarta.validation.Constraint;

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
@Constraint(validatedBy = SexTypeValidator.class)
public @interface SexValidation {
    Class<? extends Enum<?>> enumClass();
    String message() default "Invalid sex type entered";
}
