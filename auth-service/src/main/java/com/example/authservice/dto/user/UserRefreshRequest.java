package com.example.authservice.dto.user;

import lombok.Builder;

@Builder
public record UserRefreshRequest(

        String refreshToken

) {
}
