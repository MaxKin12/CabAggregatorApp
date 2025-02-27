package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import com.example.passengerservice.model.Passenger;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface PassengerMapper {

    PassengerResponse toResponse(Passenger passenger);

    Passenger toPassenger(PassengerRequest passengerRequest);

    void updatePassengerFromDto(PassengerRequest passengerRequest, @MappingTarget Passenger passenger);

}
