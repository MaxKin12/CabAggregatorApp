package com.example.passengerservice.utility.validation.impl;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_NOT_FOUND;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.utility.validation.KafkaConsumerServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceValidationImpl implements KafkaConsumerServiceValidation {

    private final PassengerRepository passengerRepository;

    @Override
    public Passenger findByIdOrThrow(UUID id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(PASSENGER_NOT_FOUND, id.toString()));
    }

    @Override
    public void updateOrThrow(Passenger passenger, RateChangeEventResponse event) {
        try {
            passenger.setRate(event.rate());
            passengerRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_PASSENGER, "update", e.getMessage());
        }
    }

}
