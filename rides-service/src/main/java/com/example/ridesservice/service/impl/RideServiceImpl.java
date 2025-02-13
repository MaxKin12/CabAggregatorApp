package com.example.ridesservice.service.impl;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RIDE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.RIDE_NOT_FOUND;

import com.example.ridesservice.dto.RidePageResponse;
import com.example.ridesservice.dto.RideRequest;
import com.example.ridesservice.dto.RideResponse;
import com.example.ridesservice.exception.custom.DbModificationAttemptException;
import com.example.ridesservice.exception.custom.RideNotFoundException;
import com.example.ridesservice.mapper.RideMapper;
import com.example.ridesservice.mapper.RidePageMapper;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.RideRepository;
import com.example.ridesservice.service.RideService;
import com.example.ridesservice.utility.pricecounter.PriceCounter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
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
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final RidePageMapper ridePageMapper;

    private final PriceCounter priceCounter;

    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    public RideResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Ride ride = findByIdOrThrow(id);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional(readOnly = true)
    public RidePageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = limit < 50 ? limit : 50;
        Page<Ride> ridePage = rideRepository.findAll(PageRequest.of(offset, limit));
        return ridePageMapper.toResponsePage(ridePage, offset, limit);
    }

    @Override
    @Transactional
    public RideResponse create(@Valid RideRequest rideRequest) {
        try {
            BigDecimal price = priceCounter.count(rideRequest.pickUpAddress(), rideRequest.destinationAddress());
            Ride saveRide = rideMapper.toRide(rideRequest, price);
            Ride ride = rideRepository.save(saveRide);
            return rideMapper.toResponse(ride);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_RIDE, "create", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public RideResponse update(@Valid RideRequest rideRequest,
                               @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Ride ride = findByIdOrThrow(id);
        try {
            rideMapper.updateRideFromDto(rideRequest, ride);
            RideResponse rideResponse = rideMapper.toResponse(ride);
            rideRepository.flush();
            return rideResponse;
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_RIDE, "update", e.getMessage())
            );
        }
    }

    @Override
    @Transactional
    public void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        findByIdOrThrow(id);
        try {
            rideRepository.deleteById(id);
        } catch (Exception e) {
            throw new DbModificationAttemptException(
                    getExceptionMessage(INVALID_ATTEMPT_CHANGE_RIDE, "delete", e.getMessage())
            );
        }
    }

    private Ride findByIdOrThrow(Long id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(getExceptionMessage(RIDE_NOT_FOUND, id)));
    }

    private String getExceptionMessage(String messageKey, Object... args) {
        return messageSource.getMessage(messageKey, args, LocaleContextHolder.getLocale());
    }

}
