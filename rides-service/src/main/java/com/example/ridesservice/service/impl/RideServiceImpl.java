package com.example.ridesservice.service.impl;

import com.example.ridesservice.dto.RidePageResponse;
import com.example.ridesservice.dto.RideRequest;
import com.example.ridesservice.dto.RideResponse;
import com.example.ridesservice.exception.DbModificationAttemptException;
import com.example.ridesservice.exception.ResourceNotFoundException;
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
        Ride ride = rideRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(messageSource
                .getMessage("exception.ride.not.found", new Object[] {id}, LocaleContextHolder.getLocale())));
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
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.ride", new Object[] {"create", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }

    @Override
    @Transactional
    public RideResponse update(@Valid RideRequest rideRequest,
                               @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Ride ride = rideRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(messageSource
                .getMessage("exception.ride.not.found", new Object[] {id}, LocaleContextHolder.getLocale())));
        try {
            rideMapper.updateRideFromDto(rideRequest, ride);
            RideResponse rideResponse = rideMapper.toResponse(ride);
            rideRepository.flush();
            return rideResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.ride", new Object[] {"update", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        try {
            rideRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(messageSource
                    .getMessage("exception.invalid.attempt.change.ride", new Object[] {"delete", e.getMessage()},
                            LocaleContextHolder.getLocale()));
        }
    }
}
