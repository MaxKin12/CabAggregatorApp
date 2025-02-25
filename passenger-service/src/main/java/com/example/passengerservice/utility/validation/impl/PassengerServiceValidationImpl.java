package com.example.passengerservice.utility.validation.impl;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_NOT_FOUND;

import com.example.passengerservice.configuration.properties.PassengerServiceProperties;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.utility.validation.PassengerServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PassengerServiceValidationImpl implements PassengerServiceValidation {

    private final PassengerServiceProperties properties;
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    public Passenger findByIdOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND, id.toString()));
    }

    @Override
    public int cutDownLimit(int limit) {
        return limit < properties.maxPageLimit() ? limit : properties.maxPageLimit();
    }

    @Override
    public Passenger saveOrThrow(Passenger passenger) {
        try {
            return passengerRepository.save(passenger);
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_PASSENGER, "create", e.getMessage());
        }
    }

    @Override
    public void updateOrThrow(Passenger passenger, PassengerRequest passengerRequest) {
        try {
            passengerMapper.updatePassengerFromDto(passengerRequest, passenger);
            passengerRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_PASSENGER, "update", e.getMessage());
        }
    }

}
