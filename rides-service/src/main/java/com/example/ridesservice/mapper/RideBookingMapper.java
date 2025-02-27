package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.ride.request.RideBookingRequest;
import com.example.ridesservice.model.Ride;
import java.math.BigDecimal;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {RideMapper.class}
)
public interface RideBookingMapper {

    Ride toRide(RideBookingRequest rideRequest, BigDecimal price);

}
