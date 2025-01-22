package com.example.passengerservice.mapper;

import com.example.passengerservice.dto.PassengerListDto;
import com.example.passengerservice.dto.PassengerRequestDto;
import com.example.passengerservice.dto.PassengerResponseDto;
import com.example.passengerservice.model.Passenger;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {
    PassengerResponseDto toResponseDto(Passenger passenger);
    Passenger toRequestModel(PassengerRequestDto passengerRequestDto);
    List<PassengerResponseDto> toListDto(List<Passenger> passengerList);
    default PassengerListDto toPassengerListDto(List<Passenger> passengerList) {
        return new PassengerListDto(toListDto(passengerList));
    }
}
