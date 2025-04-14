package com.example.authservice.dto.external;

import java.util.UUID;
import lombok.Builder;

@Builder
public record ExternalEntityResponse(

        UUID id,
        String name,
        String email,
        String phone,
        String gender

) {
}
