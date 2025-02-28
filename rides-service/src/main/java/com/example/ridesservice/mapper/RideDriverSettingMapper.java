package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.model.Ride;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RideDriverSettingMapper {

    void updateRideFromDto(RideDriverSettingRequest rideRequest, @MappingTarget Ride ride, RideStatus status);

}
