package com.example.ratesservice.mapper.rate;

import com.example.ratesservice.dto.rate.request.RateUpdateRequest;
import com.example.ratesservice.model.Rate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RateUpdateMapper {

    void updateRateFromDto(RateUpdateRequest rateUpdateRequest, @MappingTarget Rate rate);

}

