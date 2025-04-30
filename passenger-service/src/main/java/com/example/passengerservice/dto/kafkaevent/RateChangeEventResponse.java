package com.example.passengerservice.dto.kafkaevent;

import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder
public record RateChangeEventResponse(

        UUID eventId,
        UUID recipientId,
        BigDecimal rate

) {
}
