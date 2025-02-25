package com.example.passengerservice.service.impl;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_NOT_FOUND;

import com.example.passengerservice.configuration.properties.PassengerServiceProperties;
import com.example.passengerservice.dto.PassengerPageResponse;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.mapper.PassengerPageMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.PassengerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@EnableConfigurationProperties(PassengerServiceProperties.class)
public class PassengerServiceImpl implements PassengerService {

    private final PassengerServiceProperties passengerServiceProperties;

    private final PassengerRepository passengerRepository;

    private final PassengerMapper passengerMapper;

    private final PassengerPageMapper passengerPageMapper;

    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Passenger passenger = findByIdOrThrow(id);
        return passengerMapper.toResponse(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerPageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = limit < passengerServiceProperties.maxPageLimit() ? limit : passengerServiceProperties.maxPageLimit();
        Page<Passenger> passengerPage = passengerRepository.findAll(PageRequest.of(offset, limit));
        return passengerPageMapper.toResponsePage(passengerPage, offset, limit);
    }

    @Override
    @Transactional
    public PassengerResponse create(@Valid PassengerRequest passengerRequest) {
        try {
            Passenger savePassenger = passengerMapper.toPassenger(passengerRequest);
            Passenger passenger = passengerRepository.save(savePassenger);
            return passengerMapper.toResponse(passenger);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_PASSENGER, "create", e.getMessage())
            );
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
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_PASSENGER, "update", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        findByIdOrThrow(id);
        try {
            passengerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_PASSENGER, "delete", e.getMessage())
            );
        }
    }

    private Passenger findByIdOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(getExceptionMessage(PASSENGER_NOT_FOUND, id)));
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
