package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.PassengerList;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.model.Passenger;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerResponse toResponse(Passenger passenger);
    Passenger toPassenger(PassengerRequest passengerRequest);
    List<PassengerResponse> toResponseList(List<Passenger> passengerList);
    default PassengerList toPassengerList(List<Passenger> passengerList) {
        return new PassengerList(toResponseList(passengerList));
    }
}
