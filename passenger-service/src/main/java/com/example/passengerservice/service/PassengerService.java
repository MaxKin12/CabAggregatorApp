package com.example.passengerservice.service;

import com.example.passengerservice.dto.PassengerDto;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import com.example.passengerservice.mapper.PassengerMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerDto findById(@Positive(message = "id must be a positive number") Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow();
        return passengerMapper.toDto(passenger);
    }
}
