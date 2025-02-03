package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.RidePageResponse;
import com.example.ridesservice.dto.RideRequest;
import com.example.ridesservice.dto.RideResponse;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.model.Ride;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RideMapper {
    @Mapping(target = "status", ignore = true)
    RideResponse toResponse(Ride ride);

    @Mapping(target = "status", ignore = true)
    Ride toRide(RideRequest rideRequest);

    @Mapping(target = "rideList", source = "ridePage", qualifiedByName = "ridePageToRideResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "ridePage.totalPages")
    @Mapping(target = "totalElements", source = "ridePage.totalElements")
    RidePageResponse toResponsePage(Page<Ride> ridePage, int offset, int limit);

    @Mapping(target = "status", ignore = true)
    void updateRideFromDto(RideRequest rideRequest, @MappingTarget Ride ride);

    @Named("ridePageToRideResponseList")
    List<RideResponse> ridePageToRideResponseList(Page<Ride> ridePage);

    @AfterMapping
    default void setStatusField(RideRequest rideRequest, @MappingTarget Ride ride) {
        ride.setStatus(RideStatus.valueOf(rideRequest.status().toUpperCase()));
        if (ride.getPrice() == null)
            ride.setPrice(BigDecimal.TEN);
        if (ride.getOrderTime() == null)
            ride.setOrderTime(LocalDateTime.now());
    }

    @AfterMapping
    default void setStatusField(Ride ride, @MappingTarget RideResponse.RideResponseBuilder rideResponse) {
        rideResponse.status(ride.getStatus().name().toLowerCase());
    }
}
