package com.example.ratesservice.mapper.event;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.ratesservice.model.RateChangeEvent;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RateChangeEventMapper {

    RateChangeEventResponse toResponse(RateChangeEvent rateChangeEvent);

}
