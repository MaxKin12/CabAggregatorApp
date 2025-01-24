package com.example.passengerservice.service;

import com.example.passengerservice.dto.PassengerListDto;
import com.example.passengerservice.dto.PassengerRequestDto;
import com.example.passengerservice.dto.PassengerResponseDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import static com.example.passengerservice.constant.ExceptionConstantMessages.*;

public interface PassengerService {
    PassengerResponseDto findById(@Positive(message = NEGATIVE_ID_MESSAGE) Long id);
    PassengerListDto findAll();
    PassengerResponseDto create(@Valid PassengerRequestDto passengerRequestDto);
    PassengerResponseDto update(@Valid PassengerRequestDto passengerRequestDto,
                                @Positive(message = NEGATIVE_ID_MESSAGE) Long id);
    void delete(@Positive(message = NEGATIVE_ID_MESSAGE) Long id);
}
