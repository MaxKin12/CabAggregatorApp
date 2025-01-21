package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.PassengerDto;
import com.example.passengerservice.model.Passenger;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerDto toDto(Passenger passenger);
}
