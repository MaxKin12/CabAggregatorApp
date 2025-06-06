package com.example.ratesservice.utility.validation.impl;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.DRIVER_RATE_LIST_IS_EMPTY;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_RATE;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_RIDE_CONTENT;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_RIDE_STATUS;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_RATE_LIST_IS_EMPTY;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.RATE_ALREADY_EXISTS;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.RATE_NOT_FOUND;

import com.example.ratesservice.client.driver.DriverClient;
import com.example.ratesservice.dto.external.RidesResponse;
import com.example.ratesservice.exception.external.InvalidRideContentException;
import com.example.ratesservice.client.passenger.PassengerClient;
import com.example.ratesservice.client.rides.RidesClient;
import com.example.ratesservice.configuration.properties.RateServiceProperties;
import com.example.ratesservice.dto.rate.request.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.exception.custom.DbModificationAttemptException;
import com.example.ratesservice.exception.custom.RateAlreadyExistsException;
import com.example.ratesservice.exception.custom.RateListIsEmptyException;
import com.example.ratesservice.exception.custom.RateNotFoundException;
import com.example.ratesservice.mapper.rate.RateUpdateMapper;
import com.example.ratesservice.model.Rate;
import com.example.ratesservice.repository.RateRepository;
import com.example.ratesservice.utility.validation.RateServiceValidation;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateServiceValidationImpl implements RateServiceValidation {

    private final RateServiceProperties properties;
    private final RateRepository rateRepository;
    private final RateUpdateMapper rateUpdateMapper;
    private final PassengerClient passengerClient;
    private final DriverClient driverClient;
    private final RidesClient ridesClient;

    @Override
    public Rate findByIdOrThrow(UUID id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(RATE_NOT_FOUND, id.toString()));
    }

    @Override
    public int cutDownLimit(int limit) {
        return limit < properties.maxPageLimit() ? limit : properties.maxPageLimit();
    }

    @Override
    public Rate saveOrThrow(Rate rate) {
        try {
            return rateRepository.save(rate);
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RATE, "create", e.getMessage());
        }
    }

    @Override
    public void updateOrThrow(Rate rate, RateUpdateRequest rateUpdateRequest) {
        try {
            rateUpdateMapper.updateRateFromDto(rateUpdateRequest, rate);
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RATE, "update", e.getMessage());
        }
    }

    @Override
    public RidesResponse getRideById(UUID id) {
        return ridesClient.getRideById(id);
    }

    @Override
    public void checkPassengerExistence(UUID id) {
        passengerClient.getPassengerById(id);
    }

    @Override
    public void checkDriverExistence(UUID id) {
        driverClient.getDriverById(id);
    }

    @Override
    public void checkRidesRules(RidesResponse ridesResponse, Rate rate) {
        if (!Objects.equals(ridesResponse.passengerId(), rate.getPassengerId())
                || !Objects.equals(ridesResponse.driverId(), rate.getDriverId())) {
            throw new InvalidRideContentException(INVALID_RIDE_CONTENT);
        }
        if (!Objects.equals(ridesResponse.status(), "completed")) {
            throw new InvalidRideContentException(INVALID_RIDE_STATUS);
        }
    }

    @Override
    public void checkRateExistence(Rate rate) {
        if (rateRepository.existsRateByRideIdAndRecipient(rate.getRideId(), rate.getRecipient())) {
            throw new RateAlreadyExistsException(RATE_ALREADY_EXISTS);
        }
    }

    @Override
    public double countAverage(List<Rate> ratePage, UUID personId, RecipientType recipientType) {
        return ratePage
                .stream()
                .mapToDouble(Rate::getValue)
                .average()
                .orElseThrow(() -> new RateListIsEmptyException(recipientType.equals(RecipientType.PASSENGER)
                        ? PASSENGER_RATE_LIST_IS_EMPTY : DRIVER_RATE_LIST_IS_EMPTY, personId.toString())
                );
    }

    @Override
    public List<Rate> getLastRatesPage(UUID personId, RecipientType recipientType) {
        return recipientType.equals(RecipientType.PASSENGER)
                ? rateRepository.findByPassengerIdAndRecipient(
                        PageRequest.of(0, properties.lastRidesCount(),
                                Sort.by(Sort.Order.desc("id"))),
                        personId,
                        recipientType) :
                rateRepository.findByDriverIdAndRecipient(
                        PageRequest.of(0, properties.lastRidesCount(),
                                Sort.by(Sort.Order.desc("id"))),
                        personId,
                        recipientType);
    }

}
