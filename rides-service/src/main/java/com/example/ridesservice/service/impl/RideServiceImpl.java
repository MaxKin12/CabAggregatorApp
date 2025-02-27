package com.example.ridesservice.service.impl;

import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.ridesservice.dto.ride.request.RideBookingRequest;
import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import com.example.ridesservice.enums.PersonType;
import com.example.ridesservice.mapper.RideBookingMapper;
import com.example.ridesservice.mapper.RideMapper;
import com.example.ridesservice.mapper.RidePageMapper;
import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.RideRepository;
import com.example.ridesservice.service.QueueRideService;
import com.example.ridesservice.service.RideService;
import com.example.ridesservice.utility.pricecounter.PriceCounter;
import com.example.ridesservice.utility.validation.RideServiceValidation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideServiceValidation validation;
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final RideBookingMapper rideBookingMapper;
    private final RidePageMapper ridePageMapper;
    private final PriceCounter priceCounter;
    private final QueueRideService queueRideService;

    @Override
    @Transactional(readOnly = true)
    public RideResponse findById(@Positive(message = ID_NEGATIVE) Long id) {
        Ride ride = validation.findByIdOrThrow(id);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional(readOnly = true)
    public RidePageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit) {
        limit = validation.cutDownLimit(limit);
        Page<Ride> ridePage = rideRepository.findAll(PageRequest.of(offset, limit));
        return ridePageMapper.toResponsePage(ridePage, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public RidePageResponse findLastPersonRides(
            @Positive(message = ID_NEGATIVE) Long personId,
            @Min(1) Integer limit,
            PersonType personType
    ) {
        limit = validation.cutDownLimit(limit);
        Page<Ride> ridePage = validation.findLastRidesPage(personId, personType, limit);
        return ridePageMapper.toResponsePage(ridePage, 0, limit);
    }

    @Override
    @Transactional
    public RideResponse create(@Valid RideRequest rideRequest) {
        validation.checkPassengerExistence(rideRequest.passengerId());
        validation.checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());

        BigDecimal price = priceCounter.count(rideRequest.pickUpAddress(), rideRequest.destinationAddress());
        Ride ride = rideMapper.toRide(rideRequest, price);
        Ride savedRide = validation.saveOrThrow(ride);
        return rideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional
    public RideResponse bookRide(@Valid RideBookingRequest rideRequest) {
        validation.checkPassengerExistence(rideRequest.passengerId());

        BigDecimal price = priceCounter.count(rideRequest.pickUpAddress(), rideRequest.destinationAddress());
        Ride ride = rideBookingMapper.toRide(rideRequest, price);
        Ride savedRide = validation.saveOrThrow(ride);
        queueRideService.append(savedRide);
        return rideMapper.toResponse(savedRide);
    }

    @Override
    @Transactional
    public RideResponse update(@Valid RideRequest rideRequest,
                               @Positive(message = ID_NEGATIVE) Long id) {
        Ride ride = validation.findByIdOrThrow(id);
        validation.checkPassengerExistence(rideRequest.passengerId());
        validation.checkCarExistence(rideRequest.carId());
        validation.checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());

        validation.updateOrThrow(ride, rideRequest);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse setDriverToRide(@Valid RideDriverSettingRequest rideRequest) {
        validation.checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());

        QueueRide queueRide = queueRideService.popRide();
        Ride ride = validation.findByIdOrThrow(queueRide.getRideId());
        validation.updateDriverOrThrow(ride, rideRequest);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public RideResponse updateStatus(@Valid RideStatusRequest rideRequest,
                                     @Positive(message = ID_NEGATIVE) Long id) {
        Ride ride = validation.findByIdOrThrow(id);
        validation.checkStatusTransitionAllowed(ride, rideRequest);

        validation.updateStatusOrThrow(ride, rideRequest);
        return rideMapper.toResponse(ride);
    }

    @Override
    @Transactional
    public void delete(@Positive(message = ID_NEGATIVE) Long id) {
        validation.findByIdOrThrow(id);
        rideRepository.deleteById(id);
    }

}
