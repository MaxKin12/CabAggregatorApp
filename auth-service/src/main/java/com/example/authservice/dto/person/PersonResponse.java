package com.example.authservice.dto.person;

import lombok.Builder;

import java.util.UUID;

@Builder
public record PersonResponse(

        UUID id,
        String username,
        String name,
        String email,
        String phone,
        String gender

) {
}
