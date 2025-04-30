package com.example.ratesservice.service.impl;

import static com.example.ratesservice.utility.constants.LogMessagesTemplate.EVENT_PLACED_LOG_TEMPLATE;

import com.example.ratesservice.dto.external.RidesResponse;
import com.example.ratesservice.dto.rate.response.RateAverageResponse;
import com.example.ratesservice.dto.rate.response.RatePageResponse;
import com.example.ratesservice.dto.rate.request.RateRequest;
import com.example.ratesservice.dto.rate.response.RateResponse;
import com.example.ratesservice.dto.rate.request.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.exception.custom.RateListIsEmptyException;
import com.example.ratesservice.mapper.rate.RateAverageMapper;
import com.example.ratesservice.mapper.rate.RateMapper;
import com.example.ratesservice.mapper.rate.RatePageMapper;
import com.example.ratesservice.model.Rate;
import com.example.ratesservice.model.RateChangeEvent;
import com.example.ratesservice.repository.RateEventsRepository;
import com.example.ratesservice.repository.RateRepository;
import com.example.ratesservice.service.RateService;
import com.example.ratesservice.utility.validation.RateServiceValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Slf4j
@Validated
@RequiredArgsConstructor
public class RateServiceImpl implements RateService {

    private final RateServiceValidation validation;
    private final RateRepository rateRepository;
    private final RateEventsRepository rateEventsRepository;
    private final RateMapper rateMapper;
    private final RatePageMapper ratePageMapper;
    private final RateAverageMapper rateAverageMapper;

    @Override
    @Transactional(readOnly = true)
    public RateResponse findById(UUID id) {
        Rate rate = validation.findByIdOrThrow(id);
        return rateMapper.toResponse(rate);
    }

    @Override
    @Transactional(readOnly = true)
    public RatePageResponse findAllByAuthor(
            @Min(0) Integer offset,
            @Min(1) Integer limit,
            RecipientType recipientType
    ) {
        limit = validation.cutDownLimit(limit);
        Page<Rate> ratePage = rateRepository.findAllByRecipient(PageRequest.of(offset, limit), recipientType);
        return ratePageMapper.toResponsePage(ratePage, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public RateAverageResponse findAverageRate(UUID personId, RecipientType recipientType) {
        List<Rate> ratePage = validation.getLastRatesPage(personId, recipientType);
        double average = validation.countAverage(ratePage, personId, recipientType);
        BigDecimal averageDecimal = BigDecimal.valueOf(average).setScale(2, RoundingMode.CEILING);
        return rateAverageMapper.toRateAverageResponse(personId, averageDecimal);
    }

    @Override
    @Transactional
    public RateResponse create(@Valid RateRequest rateRequest) {
        Rate rate = rateMapper.toRate(rateRequest);

        RidesResponse ridesResponse = validation.getRideById(rate.getRideId());
        validation.checkRateExistence(rate);
        validation.checkPassengerExistence(rate.getPassengerId());
        validation.checkDriverExistence(rate.getDriverId());
        validation.checkRidesRules(ridesResponse, rate);

        Rate savedRate = validation.saveOrThrow(rate);
        return rateMapper.toResponse(savedRate);
    }

    @Override
    @Transactional
    public RateResponse update(@Valid RateUpdateRequest rateUpdateRequest, UUID id) {
        Rate rate = validation.findByIdOrThrow(id);
        validation.updateOrThrow(rate, rateUpdateRequest);
        return rateMapper.toResponse(rate);
    }

    @Override
    @Transactional
    public RateResponse delete(UUID id) {
        Rate rate = validation.findByIdOrThrow(id);
        rateRepository.deleteById(id);
        return rateMapper.toResponse(rate);
    }

    @Override
    @Transactional
    public void updateAverageRate(RateResponse rateResponse) {
        RecipientType recipientType = RecipientType.valueOf(rateResponse.recipient().toUpperCase());
        UUID recipientId = recipientType.equals(RecipientType.PASSENGER)
                ? rateResponse.passengerId() : rateResponse.driverId();
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
        log.info(EVENT_PLACED_LOG_TEMPLATE, recipientType, event);
    }

}
