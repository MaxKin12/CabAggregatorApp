package com.example.ratesservice.service;

import com.example.ratesservice.dto.RateAverageResponse;
import com.example.ratesservice.dto.RatePageResponse;
import com.example.ratesservice.dto.RateRequest;
import com.example.ratesservice.dto.RateResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface RateService {

    RateResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

    RatePageResponse findAll(@Min(0) Integer offset, @Min(1) @Max(50) Integer limit);

    RateAverageResponse findAveragePassengerRate(
            @Positive(message = "{validate.method.parameter.id.negative}") Long driverId
    );

    RateAverageResponse findAverageDriverRate(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

    RateResponse create(@Valid RateRequest rateRequest);

    RateResponse update(@Valid RateRequest rateRequest,
                        @Positive(message = "{validate.method.parameter.id.negative}") Long id);

    void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

}
