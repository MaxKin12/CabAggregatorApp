package com.example.authservice.dto.person;

import lombok.Builder;

@Builder
public record PersonResponse(

        String id,
        String username,
        String name,
        String email,
        String phone,
        String gender

) {
}
