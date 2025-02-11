package com.example.ratesservice.mapper;

import com.example.ratesservice.dto.RateAverageResponse;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RateAverageMapper {

    RateAverageResponse toRateAverageResponse(Long personId, BigDecimal averageValue);

}
