package com.example.ratesservice.service;

import static com.example.ratesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.ratesservice.dto.rate.RateAverageResponse;
import com.example.ratesservice.dto.rate.RatePageResponse;
import com.example.ratesservice.dto.rate.RateRequest;
import com.example.ratesservice.dto.rate.RateResponse;
import com.example.ratesservice.dto.rate.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public interface RateService {

    RateResponse findById(@Positive(message = ID_NEGATIVE) Long id);

    RatePageResponse findAllByAuthor(@Min(0) Integer offset, @Min(1) Integer limit, RecipientType recipientType);

    RateAverageResponse findAverageRate(UUID personId, RecipientType recipientType);

    RateResponse create(@Valid RateRequest rateRequest);

    RateResponse update(@Valid RateUpdateRequest rateUpdateRequest, @Positive(message = ID_NEGATIVE) Long id);

    RateResponse delete(@Positive(message = ID_NEGATIVE) Long id);

    void updateAverageRate(RateResponse rateResponse);

}
