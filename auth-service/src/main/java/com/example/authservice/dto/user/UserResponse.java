package com.example.authservice.dto.user;

import lombok.Builder;

@Builder
public record UserResponse(

        String id,
        String username,
        String firstName,
        String name,
        String email

) {
}
