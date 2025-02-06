package com.example.passengerservice.service.impl;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_NOT_FOUND;

import com.example.passengerservice.dto.PassengerPageResponse;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.PassengerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;

    private final MessageSource messageSource;

    @Override
    public PassengerResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Passenger passenger = findByIdOrThrow(id);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    public PassengerPageResponse findAll(@Min(0) Integer offset, @Min(1) @Max(50) Integer limit) {
        Page<Passenger> passengerPage = passengerRepository.findAll(PageRequest.of(offset, limit));
        return passengerMapper.toResponsePage(passengerPage, offset, limit);
    }

    @Override
    public PassengerResponse create(@Valid PassengerRequest passengerRequest) {
        try {
            Passenger savePassenger = passengerMapper.toPassenger(passengerRequest);
            Passenger passenger = passengerRepository.save(savePassenger);
            return passengerMapper.toResponse(passenger);
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("create", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public PassengerResponse update(@Valid PassengerRequest passengerRequest,
                                    @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Passenger passenger = findByIdOrThrow(id);
        try {
            passengerMapper.updatePassengerFromDto(passengerRequest, passenger);
            PassengerResponse passengerResponse = passengerMapper.toResponse(passenger);
            passengerRepository.flush();
            return passengerResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("update", e.getMessage()));
        }
    }

    @Override
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        findByIdOrThrow(id);
        try {
            passengerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("delete", e.getMessage()));
        }
    }

    private Passenger findByIdOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(getPassengerNotFoundExceptionMessage(id)));
    }

    private String getPassengerNotFoundExceptionMessage(Long id) {
        return messageSource
                .getMessage(PASSENGER_NOT_FOUND, new Object[] {id}, LocaleContextHolder.getLocale());
    }

    private String getInvalidAttemptExceptionMessage(String methodName, String exceptionMessage) {
        return messageSource
                .getMessage(INVALID_ATTEMPT_CHANGE_PASSENGER, new Object[] {methodName, exceptionMessage},
                        LocaleContextHolder.getLocale());
    }
}
