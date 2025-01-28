package com.example.driverservice.dto.driver;

import com.example.driverservice.model.enums.Sex;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import static com.example.driverservice.constant.RegularExpressionsConstants.PHONE_NUMBER_CHECK;

public record DriverRequest(
        @NotBlank(message = "Name is a necessary field")
        @Size(max = 50, message = "Name is too long")
        String name,
        @NotBlank(message = "Email is a necessary field")
        @Size(max = 100, message = "Email is too long")
        @Email(message = "Invalid email entered")
        String email,
        @NotBlank(message = "Phone is a necessary field")
        @Size(max = 13, message = "Phone number is to long")
        @Pattern(regexp = PHONE_NUMBER_CHECK, message = "Entered phone number format doesn't supported")
        String phone,
        @Positive(message = "Car id must be a positive number")
        Long carId
) {}
