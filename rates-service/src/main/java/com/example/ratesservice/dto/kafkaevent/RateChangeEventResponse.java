package com.example.ratesservice.dto.kafkaevent;

import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record RateChangeEventResponse(

        Long eventId,
        Long recipientId,
        BigDecimal rate

) {
}
