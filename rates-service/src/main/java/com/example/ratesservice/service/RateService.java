package com.example.ratesservice.service;

import com.example.ratesservice.dto.rate.response.RateAverageResponse;
import com.example.ratesservice.dto.rate.response.RatePageResponse;
import com.example.ratesservice.dto.rate.request.RateRequest;
import com.example.ratesservice.dto.rate.response.RateResponse;
import com.example.ratesservice.dto.rate.request.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public interface RateService {

    RateResponse findById(UUID id);

    RatePageResponse findAllByAuthor(@Min(0) Integer offset, @Min(1) Integer limit, RecipientType recipientType);

    RateAverageResponse findAverageRate(UUID personId, RecipientType recipientType);

    RateResponse create(@Valid RateRequest rateRequest);

    RateResponse update(@Valid RateUpdateRequest rateUpdateRequest, UUID id);

    RateResponse delete(UUID id);

    void updateAverageRate(RateResponse rateResponse);

}
