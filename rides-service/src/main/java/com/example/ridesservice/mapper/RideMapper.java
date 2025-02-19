package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.request.RideRequest;
import com.example.ridesservice.dto.response.RideResponse;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.model.Ride;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RideMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToLowerCaseString")
    RideResponse toResponse(Ride ride);

    @Mapping(target = "status", source = "rideRequest.status", qualifiedByName = "stringToUpperCaseStatus")
    @Mapping(target = "price", ignore = true)
    Ride toRide(RideRequest rideRequest, BigDecimal price);

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToUpperCaseStatus")
    void updateRideFromDto(RideRequest rideRequest, @MappingTarget Ride ride);

    @Named("statusToLowerCaseString")
    default String statusToLowerCaseString(RideStatus rideStatus) {
        return rideStatus.name().toLowerCase();
    }

    @Named("stringToUpperCaseStatus")
    default RideStatus stringToUpperCaseStatus(String status) {
        return RideStatus.valueOf(status.toUpperCase());
    }

    @AfterMapping
    default void setPriceAndOrderTimeFields(BigDecimal price, @MappingTarget Ride ride) {
        if (ride.getPrice() == null) {
            ride.setPrice(price);
        }
        if (ride.getOrderTime() == null) {
            ride.setOrderTime(LocalDateTime.now());
        }
    }

}
