package com.example.passengerservice.service;

import com.example.passengerservice.dto.PassengerListDto;
import com.example.passengerservice.dto.PassengerRequestDto;
import com.example.passengerservice.dto.PassengerResponseDto;
import com.example.passengerservice.exception.DBException;
import com.example.passengerservice.exception.ResourceNotFoundException;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import com.example.passengerservice.mapper.PassengerMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerResponseDto findById(@Positive(message = "id must be a positive number") Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(
                "Passenger with id \"" + id + "\" not found"));
        return passengerMapper.toResponseDto(passenger);
    }

    public PassengerListDto findAll() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengerMapper.toPassengerListDto(passengers);
    }

    public PassengerResponseDto create(@Valid PassengerRequestDto passengerRequestDto) {
        try {
            Passenger passenger = passengerRepository.save(passengerMapper.toRequestModel(passengerRequestDto));
            return passengerMapper.toResponseDto(passenger);
        } catch (Exception e) {
            throw new DBException(e);
        }
    }

    @Transactional
    public PassengerResponseDto update(@Valid PassengerRequestDto passengerRequestDto,
                          @Positive(message = "id must be non-negative number") Long id) {
        Passenger passenger = passengerRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Passenger with id \"" + id + "\" not found"));
        try {
            Passenger editedPassenger = passengerMapper.toRequestModel(passengerRequestDto);
            passenger.setName(editedPassenger.getName());
            passenger.setEmail(editedPassenger.getEmail());
            passenger.setPhone(editedPassenger.getPhone());
            passenger.setPassword(editedPassenger.getPassword());
            return passengerMapper.toResponseDto(passenger);
        } catch (Exception e) {
            throw new DBException("Invalid attempt to update passenger: " + e.getMessage());
        }
    }

    public void delete(@Positive(message = "id must be positive number") Long id) {
        try {
            passengerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DBException("Invalid attempt to delete passenger: " + e.getMessage());
        }
    }
}
