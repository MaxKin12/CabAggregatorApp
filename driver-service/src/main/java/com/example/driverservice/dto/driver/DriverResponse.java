package com.example.driverservice.dto.driver;

import com.example.driverservice.dto.EntityResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
@Builder
public record DriverResponse(

        UUID id,
        String name,
        String email,
        String phone,
        String gender,
        BigDecimal rate,
        List<Long> carIds

) implements EntityResponse {
}
