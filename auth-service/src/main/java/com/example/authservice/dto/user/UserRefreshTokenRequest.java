package com.example.authservice.dto.user;

import lombok.Builder;

@Builder
public record UserRefreshTokenRequest(

        String refreshToken

) {
}
