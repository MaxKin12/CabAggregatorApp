package com.example.authservice.dto.user;

import java.util.UUID;
import lombok.Builder;

@Builder
public record UserResponse(

        UUID id,
        String username,
        String firstName,
        String name,
        String email

) {
}
