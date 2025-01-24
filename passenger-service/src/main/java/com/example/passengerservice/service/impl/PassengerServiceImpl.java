package com.example.passengerservice.service.impl;

import com.example.passengerservice.dto.PassengerListDto;
import com.example.passengerservice.dto.PassengerRequestDto;
import com.example.passengerservice.dto.PassengerResponseDto;
import com.example.passengerservice.exception.DBException;
import com.example.passengerservice.exception.ResourceNotFoundException;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.PassengerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import com.example.passengerservice.mapper.PassengerMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.example.passengerservice.constant.ExceptionConstantMessages.*;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerResponseDto findById(@Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(
                String.format(PASSENGER_NOT_FOUND_MESSAGE, id)));
        return passengerMapper.toResponseDto(passenger);
    }

    public PassengerListDto findAll() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengerMapper.toPassengerListDto(passengers);
    }

    @Transactional
    public PassengerResponseDto create(@Valid PassengerRequestDto passengerRequestDto) {
        try {
            Passenger passenger = passengerRepository.save(passengerMapper.toRequestModel(passengerRequestDto));
            return passengerMapper.toResponseDto(passenger);
        } catch (Exception e) {
            throw new DBException(String.format(INVALID_ATTEMPT_MESSAGE, "create", e.getMessage()));
        }
    }

    @Transactional
    public PassengerResponseDto update(@Valid PassengerRequestDto passengerRequestDto,
                          @Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(String.format(PASSENGER_NOT_FOUND_MESSAGE, id)));
        try {
            Passenger editedPassenger = passengerMapper.toRequestModel(passengerRequestDto);
            passenger.setName(editedPassenger.getName());
            passenger.setEmail(editedPassenger.getEmail());
            passenger.setPhone(editedPassenger.getPhone());
            PassengerResponseDto passengerResponseDto = passengerMapper.toResponseDto(passenger);
            passengerRepository.flush();
            return passengerResponseDto;
        } catch (Exception e) {
            throw new DBException(String.format(INVALID_ATTEMPT_MESSAGE, "update", e.getMessage()));
        }
    }

    @Transactional
    public void delete(@Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        try {
            passengerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DBException(String.format(INVALID_ATTEMPT_MESSAGE, "delete", e.getMessage()));
        }
    }
}
