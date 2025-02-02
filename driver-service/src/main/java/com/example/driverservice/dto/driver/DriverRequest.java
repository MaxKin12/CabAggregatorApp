package com.example.driverservice.dto.driver;

import com.example.driverservice.enums.Sex;
import com.example.driverservice.enums.annotation.SexValidation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import static com.example.driverservice.constant.RegularExpressionsConstants.PHONE_NUMBER_CHECK;

public record DriverRequest(
        @NotBlank(message = "{validate.field.name.blank}")
        @Size(max = 50, message = "{validate.field.name.too-long}")
        String name,
        @NotBlank(message = "{validate.field.email.blank}")
        @Size(max = 100, message = "{validate.field.email.too-long}")
        @Email(message = "{validate.field.email.invalid.pattern}")
        String email,
        @NotBlank(message = "{validate.field.phone.blank}")
        @Size(max = 13, message = "{validate.field.phone.too-long}")
        @Pattern(regexp = PHONE_NUMBER_CHECK, message = "{validate.field.phone.invalid.pattern")
        String phone,
        @NotBlank(message = "{validate.field.sex.blank}")
        @SexValidation(enumClass = Sex.class, message = "{validate.field.sex.invalid.pattern}")
        String sex
) {}
