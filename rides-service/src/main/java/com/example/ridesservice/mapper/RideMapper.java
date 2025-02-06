package com.example.ridesservice.mapper;

import com.example.ridesservice.dto.RidePageResponse;
import com.example.ridesservice.dto.RideRequest;
import com.example.ridesservice.dto.RideResponse;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.model.Ride;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RideMapper {

    @Mapping(target = "status", source = "status", qualifiedByName = "statusToLowerCaseString")
    RideResponse toResponse(Ride ride);

    @Mapping(target = "status", source = "rideRequest.status", qualifiedByName = "stringToUpperCaseStatus")
    @Mapping(target = "price", ignore = true)
    Ride toRide(RideRequest rideRequest, BigDecimal price);

    @Mapping(target = "rideList", source = "ridePage", qualifiedByName = "ridePageToRideResponseList")
    @Mapping(target = "currentPageNumber", source = "offset")
    @Mapping(target = "pageLimit", source = "limit")
    @Mapping(target = "totalPages", source = "ridePage.totalPages")
    @Mapping(target = "totalElements", source = "ridePage.totalElements")
    RidePageResponse toResponsePage(Page<Ride> ridePage, int offset, int limit);

    @Mapping(target = "status", source = "status", qualifiedByName = "stringToUpperCaseStatus")
    void updateRideFromDto(RideRequest rideRequest, @MappingTarget Ride ride);

    @Named("ridePageToRideResponseList")
    List<RideResponse> ridePageToRideResponseList(Page<Ride> ridePage);

    @Named("statusToLowerCaseString")
    default String statusToLowerCaseString(RideStatus rideStatus) {
        return rideStatus.name().toLowerCase();
    }

    @Named("stringToUpperCaseStatus")
    default RideStatus stringToUpperCaseStatus(String status) {
        return RideStatus.valueOf(status.toUpperCase());
    }

    @AfterMapping
    default void setStatusField(BigDecimal price, @MappingTarget Ride ride) {
        if (ride.getPrice() == null) {
            ride.setPrice(price);
        }
        if (ride.getOrderTime() == null) {
            ride.setOrderTime(LocalDateTime.now());
        }
    }

}
