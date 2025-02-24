package com.example.ratesservice.service.impl;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RATE;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_RIDE_CONTENT;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_ALREADY_EXISTS;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_DRIVER_LIST_IS_EMPTY;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_NOT_FOUND;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionVariablesConstants.RATE_PASSENGER_LIST_IS_EMPTY;

import com.example.ratesservice.configuration.properties.RateServiceProperties;
import com.example.ratesservice.client.DriverClient;
import com.example.ratesservice.client.PassengerClient;
import com.example.ratesservice.client.RidesClient;
import com.example.ratesservice.client.dto.RidesResponse;
import com.example.ratesservice.client.exception.InvalidRideContentException;
import com.example.ratesservice.configuration.properties.RateServiceProperties;
import com.example.ratesservice.dto.rate.RateAverageResponse;
import com.example.ratesservice.dto.rate.RatePageResponse;
import com.example.ratesservice.dto.rate.RateRequest;
import com.example.ratesservice.dto.rate.RateResponse;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.exception.custom.DbModificationAttemptException;
import com.example.ratesservice.exception.custom.RateAlreadyExistsException;
import com.example.ratesservice.exception.custom.RateListIsEmptyException;
import com.example.ratesservice.exception.custom.RateNotFoundException;
import com.example.ratesservice.mapper.rate.RateAverageMapper;
import com.example.ratesservice.mapper.rate.RateMapper;
import com.example.ratesservice.mapper.rate.RatePageMapper;
import com.example.ratesservice.model.Rate;
import com.example.ratesservice.model.RateChangeEvent;
import com.example.ratesservice.repository.RateEventsRepository;
import com.example.ratesservice.repository.RateRepository;
import com.example.ratesservice.service.RateService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@EnableConfigurationProperties(RateServiceProperties.class)
public class RateServiceImpl implements RateService {

    private final RateServiceProperties rateServiceProperties;

    private final MessageSource messageSource;

    private final RateRepository rateRepository;

    private final RateEventsRepository rateEventsRepository;

    private final RateMapper rateMapper;

    private final RatePageMapper ratePageMapper;

    private final RateAverageMapper rateAverageMapper;

    private final MessageSource messageSource;

    private final RidesClient ridesClient;

    private final PassengerClient passengerClient;

    private final DriverClient driverClient;

    private final RateEventsRepository rateEventsRepository;

    @Override
    @Transactional(readOnly = true)
    public RateResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Rate rate = findByIdOrThrow(id);
        return rateMapper.toResponse(rate);
    }

    @Override
    @Transactional(readOnly = true)
    public RatePageResponse findAllByAuthor(@Min(0) Integer offset, @Min(1) Integer limit, AuthorType authorType) {
        limit = limit < rateServiceProperties.maxPageLimit() ? limit : rateServiceProperties.maxPageLimit();
        Page<Rate> ratePage = rateRepository.findAllByAuthor(PageRequest.of(offset, limit), authorType);
        return ratePageMapper.toResponsePage(ratePage, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public RateAverageResponse findAverageRate(
            @Positive(message = "{validate.method.parameter.id.negative}") Long personId,
            RecipientType recipientType
    ) {
        List<Rate> ratePage = recipientType.equals(RecipientType.PASSENGER) ?
                rateRepository.findByPassengerIdAndRecipient(
                        PageRequest.of(0, rateServiceProperties.lastRidesCount(),
                                Sort.by(Sort.Order.desc("id"))),
                        personId,
                        recipientType) :
                rateRepository.findByDriverIdAndRecipient(
                        PageRequest.of(0, rateServiceProperties.lastRidesCount(),
                                Sort.by(Sort.Order.desc("id"))),
                        personId,
                        recipientType);
        double average = ratePage
                .stream()
                .mapToDouble(Rate::getValue)
                .average()
                .orElseThrow(() -> new RateListIsEmptyException(
                        recipientType.equals(RecipientType.PASSENGER) ?
                        getExceptionMessage(RATE_PASSENGER_LIST_IS_EMPTY, personId) :
                        getExceptionMessage(RATE_DRIVER_LIST_IS_EMPTY, personId))
                );
        BigDecimal averageDecimal = BigDecimal.valueOf(average).setScale(2, RoundingMode.CEILING);
        return rateAverageMapper.toRateAverageResponse(personId, averageDecimal);
    }

    @Override
    @Transactional
    public RateResponse create(@Valid RateRequest rateRequest) {
        Rate saveRate = rateMapper.toRate(rateRequest);
        ifRateAlreadyExistsThrow(saveRate);
        RidesResponse ridesResponse = getRideById(saveRate.getRideId());
        checkPassengerById(saveRate.getPassengerId());
        checkDriverById(saveRate.getDriverId());
        checkRidesRules(ridesResponse, saveRate);
        try {
            Rate rate = rateRepository.save(saveRate);
            return rateMapper.toResponse(rate);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_RATE, "create", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public RateResponse update(@Valid RateRequest rateRequest,
                               @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Rate rate = findByIdOrThrow(id);
        RidesResponse ridesResponse = getRideById(rate.getRideId());
        checkPassengerById(rate.getPassengerId());
        checkDriverById(rate.getDriverId());
        checkRidesRules(ridesResponse, rate);
        try {
            rateMapper.updateRateFromDto(rateRequest, rate);
            RateResponse rateResponse = rateMapper.toResponse(rate);
            rateRepository.flush();
            return rateResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_RATE, "update", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public RateResponse delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Rate rate = findByIdOrThrow(id);
        try {
            rateRepository.deleteById(id);
            return rateMapper.toResponse(rate);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_RATE, "delete", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public void updateAverageRate(RateResponse rateResponse) {
        RecipientType recipientType = RecipientType.valueOf(rateResponse.recipient().toUpperCase());
        Long recipientId = recipientType.equals(RecipientType.PASSENGER) ?
                rateResponse.passengerId() : rateResponse.driverId();
        RateAverageResponse rateAverageResponse;
        try {
            rateAverageResponse = findAverageRate(recipientId, recipientType);
        } catch (RateListIsEmptyException e) {
            return;
        }
        RateChangeEvent event = RateChangeEvent
                .builder()
                .recipientId(recipientId)
                .recipientType(recipientType)
                .rate(rateAverageResponse.averageValue())
                .build();
        rateEventsRepository.save(event);
    }

    private RidesResponse getRideById(Long id) {
        return ridesClient.getRideById(id).getBody();
    }

    private void checkPassengerById(Long id) {
        passengerClient.getPassengerById(id).getBody();
    }

    private void checkDriverById(Long id) {
        driverClient.getDriverById(id).getBody();
    }

    private void checkRidesRules(RidesResponse ridesResponse, Rate rate) {
        if (!Objects.equals(ridesResponse.passengerId(), rate.getPassengerId())
                || !Objects.equals(ridesResponse.driverId(), rate.getDriverId())) {
            throw new InvalidRideContentException(getExceptionMessage(INVALID_RIDE_CONTENT));
        }
    }

    private Rate findByIdOrThrow(Long id) {
        return rateRepository.findById(id)
                .orElseThrow(() -> new RateNotFoundException(getExceptionMessage(RATE_NOT_FOUND, id)));
    }

    private void ifRateAlreadyExistsThrow(Rate rate) {
        if (rateRepository.existsRateByRideIdAndRecipient(rate.getRideId(), rate.getRecipient())) {
            throw new RateAlreadyExistsException(getExceptionMessage(RATE_ALREADY_EXISTS));
        }
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
