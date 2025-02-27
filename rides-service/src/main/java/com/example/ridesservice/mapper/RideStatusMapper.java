package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.model.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RideMapper.class}
)
public interface RideStatusMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToUpperCaseStatus")
    void updateRideFromDto(RideStatusRequest rideRequest, @MappingTarget Ride ride);

}
