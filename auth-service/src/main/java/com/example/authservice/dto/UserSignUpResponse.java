package com.example.authservice.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserSignUpResponse(

        UUID id,
        String username,
        String name,
        String email,
        String phone,
        String gender

) {
}
