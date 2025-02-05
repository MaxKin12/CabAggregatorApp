package com.example.ridesservice.service.impl;

import com.example.ridesservice.dto.RidePageResponse;
import com.example.ridesservice.dto.RideRequest;
import com.example.ridesservice.dto.RideResponse;
import com.example.ridesservice.exception.custom.DbModificationAttemptException;
import com.example.ridesservice.exception.custom.ResourceNotFoundException;
import com.example.ridesservice.mapper.RideMapper;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.RideRepository;
import com.example.ridesservice.service.RideService;
import com.example.ridesservice.utility.pricecounter.PriceCounter;
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

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RIDE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.RIDE_NOT_FOUND;

@Service
@Validated
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final PriceCounter priceCounter;
    private final MessageSource messageSource;

    @Override
    public RideResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(getRideNotFoundExceptionMessage(id)));
        return rideMapper.toResponse(ride);
    }

    @Override
    public RidePageResponse findAll(@Min(0) Integer offset, @Min(1) @Max(50) Integer limit) {
        Page<Ride> ridePage = rideRepository.findAll(PageRequest.of(offset, limit));
        return rideMapper.toResponsePage(ridePage, offset, limit);
    }

    @Override
    public RideResponse create(@Valid RideRequest rideRequest) {
        try {
            Ride saveRide = rideMapper.toRide(rideRequest, priceCounter);
            Ride ride = rideRepository.save(saveRide);
            return rideMapper.toResponse(ride);
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("create", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public RideResponse update(@Valid RideRequest rideRequest,
                               @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Ride ride = rideRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException(getRideNotFoundExceptionMessage(id)));
        try {
            rideMapper.updateRideFromDto(rideRequest, ride);
            RideResponse rideResponse = rideMapper.toResponse(ride);
            rideRepository.flush();
            return rideResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("update", e.getMessage()));
        }
    }

    @Override
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        try {
            rideRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(getInvalidAttemptExceptionMessage("delete", e.getMessage()));
        }
    }

    private String getRideNotFoundExceptionMessage(Long id) {
        return messageSource
                .getMessage(RIDE_NOT_FOUND, new Object[] {id}, LocaleContextHolder.getLocale());
    }

    private String getInvalidAttemptExceptionMessage(String methodName, String exceptionMessage) {
        return messageSource
                .getMessage(INVALID_ATTEMPT_CHANGE_RIDE, new Object[] {methodName, exceptionMessage},
                        LocaleContextHolder.getLocale());
    }
}
