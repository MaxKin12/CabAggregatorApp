package com.example.passengerservice.service.impl;

import com.example.passengerservice.dto.PassengerList;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.exception.DBModificationAttemptException;
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

import static com.example.passengerservice.constant.ExceptionMessagesConstants.INVALID_ATTEMPT_MESSAGE;
import static com.example.passengerservice.constant.ExceptionMessagesConstants.NEGATIVE_ID_MESSAGE;
import static com.example.passengerservice.constant.ExceptionMessagesConstants.PASSENGER_NOT_FOUND_MESSAGE;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    public PassengerResponse findById(@Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(PASSENGER_NOT_FOUND_MESSAGE.formatted(id)));
        return passengerMapper.toResponse(passenger);
    }

    public PassengerList findAll() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengerMapper.toPassengerList(passengers);
    }

    @Transactional
    public PassengerResponse create(@Valid PassengerRequest passengerRequest) {
        try {
            Passenger passenger = passengerRepository.save(passengerMapper.toPassenger(passengerRequest));
            return passengerMapper.toResponse(passenger);
        } catch (Exception e) {
            throw new DBModificationAttemptException(INVALID_ATTEMPT_MESSAGE.formatted("create", e.getMessage()));
        }
    }

    @Transactional
    public PassengerResponse update(@Valid PassengerRequest passengerRequest,
                                    @Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(PASSENGER_NOT_FOUND_MESSAGE.formatted(id)));
        try {
            Passenger editedPassenger = passengerMapper.toPassenger(passengerRequest);
            passenger.setName(editedPassenger.getName());
            passenger.setEmail(editedPassenger.getEmail());
            passenger.setPhone(editedPassenger.getPhone());
            PassengerResponse passengerResponse = passengerMapper.toResponse(passenger);
            passengerRepository.flush();
            return passengerResponse;
        } catch (Exception e) {
            throw new DBModificationAttemptException(INVALID_ATTEMPT_MESSAGE.formatted("update", e.getMessage()));
        }
    }

    @Transactional
    public void delete(@Positive(message = NEGATIVE_ID_MESSAGE) Long id) {
        try {
            passengerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DBModificationAttemptException(INVALID_ATTEMPT_MESSAGE.formatted("delete", e.getMessage()));
        }
    }
}
