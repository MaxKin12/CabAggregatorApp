package com.example.passengerservice.service.impl;

import com.example.passengerservice.dto.PassengerResponseList;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.exception.DbModificationAttemptException;
import com.example.passengerservice.exception.ResourceNotFoundException;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.PassengerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import com.example.passengerservice.mapper.PassengerMapper;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final MessageSource messageSource;

    @Override
    public PassengerResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(messageSource
                        .getMessage("exception.passenger.not.found",
                                new Object[] {id}, LocaleContextHolder.getLocale())));
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerResponseList findAll() {
        List<Passenger> passengers = passengerRepository.findAll();
        return passengerMapper.toResponseList(passengers);
    }

    @Override
    public PassengerResponse create(@Valid PassengerRequest passengerRequest) {
        try {
            Passenger passenger = passengerRepository.save(passengerMapper.toPassenger(passengerRequest));
            return passengerMapper.toResponse(passenger);
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.entity",
                            new Object[] {"create", e.getMessage()}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @Transactional
    public PassengerResponse update(@Valid PassengerRequest passengerRequest,
                                    @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(messageSource
                        .getMessage("validate.exception.passenger.not.found",
                                new Object[] {id}, LocaleContextHolder.getLocale())));
        try {
            Passenger editedPassenger = passengerMapper.toPassenger(passengerRequest);
            passenger.setName(editedPassenger.getName());
            passenger.setEmail(editedPassenger.getEmail());
            passenger.setPhone(editedPassenger.getPhone());
            PassengerResponse passengerResponse = passengerMapper.toResponse(passenger);
            passengerRepository.flush();
            return passengerResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.entity",
                            new Object[] {"update", e.getMessage()}, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        try {
            passengerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.entity",
                            new Object[] {"delete", e.getMessage()}, LocaleContextHolder.getLocale()));
        }
    }
}
