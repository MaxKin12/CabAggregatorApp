package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.dto.PassengerResponseList;
import com.example.passengerservice.model.Passenger;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class PassengerMapper {
    public abstract PassengerResponse toResponse(Passenger passenger);

    public abstract Passenger toPassenger(PassengerRequest passengerRequest);

    public PassengerResponseList toResponseList(List<Passenger> passengerList) {
        return new PassengerResponseList(toList(passengerList));
    }

    protected abstract List<PassengerResponse> toList(List<Passenger> passengerList);
}
