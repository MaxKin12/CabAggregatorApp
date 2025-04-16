package com.example.authservice.dto.user;

import lombok.Builder;

@Builder
public record AuthResponse(

        String accessToken,
        String refreshToken,
        Long expirationTime

) {
}
