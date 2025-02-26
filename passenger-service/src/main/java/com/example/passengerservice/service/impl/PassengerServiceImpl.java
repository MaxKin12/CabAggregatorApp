package com.example.passengerservice.service.impl;

import static com.example.passengerservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.mapper.PassengerPageMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.PassengerService;
import com.example.passengerservice.utility.validation.PassengerServiceValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerServiceValidation validation;
    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;
    private final PassengerPageMapper passengerPageMapper;

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse findById(@Positive(message = ID_NEGATIVE) Long id) {
        Passenger passenger = validation.findByIdOrThrow(id);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerPageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = validation.cutDownLimit(limit);
        Page<Passenger> passengerPage = passengerRepository.findAll(PageRequest.of(offset, limit));
        return passengerPageMapper.toResponsePage(passengerPage, offset, limit);
    }

    @Override
    @Transactional
    public PassengerResponse create(@Valid PassengerRequest passengerRequest) {
        Passenger passenger = passengerMapper.toPassenger(passengerRequest);
        Passenger savedPassenger = validation.saveOrThrow(passenger);
        return passengerMapper.toResponse(savedPassenger);
    }

    @Override
    @Transactional
    public PassengerResponse update(@Valid PassengerRequest passengerRequest,
                                    @Positive(message = ID_NEGATIVE) Long id) {
        Passenger passenger = validation.findByIdOrThrow(id);
        validation.updateOrThrow(passenger, passengerRequest);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    @Transactional
    public void updateRate(RateChangeEventResponse event) {
        Passenger passenger = findByIdOrThrow(event.recipientId());
        passenger.setRate(event.rate());
        passengerRepository.save(passenger);
    }

    @Override
    @Transactional
    public void delete(@Positive(message = ID_NEGATIVE) Long id) {
        validation.findByIdOrThrow(id);
        passengerRepository.deleteById(id);
    }

}
