package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.PassengerResponseList;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.model.Passenger;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PassengerMapper {
    public abstract PassengerResponse toResponse(Passenger passenger);
    public abstract Passenger toPassenger(PassengerRequest passengerRequest);
    public PassengerResponseList toResponseList(List<Passenger> passengerList) {
        return new PassengerResponseList(toList(passengerList));
    }
    protected abstract List<PassengerResponse> toList(List<Passenger> passengerList);
}
