package com.example.driverservice.utility.validation.impl;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_DRIVER;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.utility.validation.KafkaConsumerServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceValidationImpl implements KafkaConsumerServiceValidation {

    private final DriverRepository driverRepository;

    @Override
    public Driver findByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DRIVER_NOT_FOUND, String.valueOf(id)));
    }

    @Override
    public void updateOrThrow(Driver driver, RateChangeEventResponse event) {
        try {
            driver.setRate(event.rate());
            driverRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_DRIVER, "update", e.getMessage());
        }
    }

}
