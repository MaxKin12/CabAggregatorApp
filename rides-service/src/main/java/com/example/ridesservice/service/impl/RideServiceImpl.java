package com.example.ridesservice.service.impl;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_NOT_CONTAINS_CAR;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RIDE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.RIDE_CONTAINS_DRIVER;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.RIDE_NOT_FOUND;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.WRONG_STATUS_CHANGE;

import com.example.ridesservice.client.driver.DriverClient;
import com.example.ridesservice.client.driver.dto.CarResponse;
import com.example.ridesservice.client.driver.dto.DriverResponse;
import com.example.ridesservice.client.driver.exception.DriverNotContainsCarException;
import com.example.ridesservice.client.passenger.PassengerClient;
import com.example.ridesservice.dto.request.RideBookingRequest;
import com.example.ridesservice.dto.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.request.RideRequest;
import com.example.ridesservice.dto.request.RideStatusRequest;
import com.example.ridesservice.dto.response.RidePageResponse;
import com.example.ridesservice.dto.response.RideResponse;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.exception.custom.DbModificationAttemptException;
import com.example.ridesservice.exception.custom.RideNotContainsDriverException;
import com.example.ridesservice.exception.custom.RideNotFoundException;
import com.example.ridesservice.exception.custom.WrongStatusChangeException;
import com.example.ridesservice.mapper.RideBookingMapper;
import com.example.ridesservice.mapper.RideDriverSettingMapper;
import com.example.ridesservice.mapper.RideMapper;
import com.example.ridesservice.mapper.RidePageMapper;
import com.example.ridesservice.mapper.RideStatusMapper;
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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
public class RideServiceImpl implements RideService {

    private final RideRepository rideRepository;

    private final RideMapper rideMapper;

    private final RideBookingMapper rideBookingMapper;

    private final RideDriverSettingMapper rideDriverMapper;

    private final RideStatusMapper rideStatusMapper;

    private final RidePageMapper ridePageMapper;

    private final PriceCounter priceCounter;

    private final MessageSource messageSource;

    private final PassengerClient passengerClient;

    private final DriverClient driverClient;

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
    @Transactional(readOnly = true)
    public RidePageResponse findLastPassengerRides(
            @Positive(message = "{validate.method.parameter.id.negative}") Long passengerId,
            @Min(1) Integer limit
    ) {
        int offset = 0;
        limit = limit < 50 ? limit : 50;
        Page<Ride> ridePage = rideRepository.findByPassengerId(
                PageRequest.of(offset, limit, Sort.by(Sort.Order.desc("id"))),
                passengerId
        );
        return ridePageMapper.toResponsePage(ridePage, offset, limit);
    }

    @Override
    @Transactional(readOnly = true)
    public RidePageResponse findLastDriverRides(
            @Positive(message = "{validate.method.parameter.id.negative}") Long driverId,
            @Min(1) Integer limit
    ) {
        int offset = 0;
        limit = limit < 50 ? limit : 50;
        Page<Ride> ridePage = rideRepository.findByDriverId(
                PageRequest.of(offset, limit, Sort.by(Sort.Order.desc("id"))),
                driverId
        );
        return ridePageMapper.toResponsePage(ridePage, offset, limit);
    }

    @Override
    @Transactional
    public RideResponse create(@Valid RideRequest rideRequest) {
        checkPassengerExistenceById(rideRequest.passengerId());
        DriverResponse driverResponse = getDriverById(rideRequest.driverId());
        CarResponse carResponse = getCarById(rideRequest.carId());
        checkDriverHasCar(driverResponse, carResponse);
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
    public RideResponse book(@Valid RideBookingRequest rideRequest) {
        checkPassengerExistenceById(rideRequest.passengerId());
        try {
            BigDecimal price = priceCounter.count(rideRequest.pickUpAddress(), rideRequest.destinationAddress());
            Ride saveRide = rideBookingMapper.toRide(rideRequest, price);
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
        checkPassengerExistenceById(rideRequest.passengerId());
        DriverResponse driverResponse = getDriverById(rideRequest.driverId());
        CarResponse carResponse = getCarById(rideRequest.carId());
        checkDriverHasCar(driverResponse, carResponse);
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
    public RideResponse setDriverToRide(@Valid RideDriverSettingRequest rideRequest,
                                        @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Ride ride = findByIdOrThrow(id);
        checkRideHasDriver(ride);
        DriverResponse driverResponse = getDriverById(rideRequest.driverId());
        CarResponse carResponse = getCarById(rideRequest.carId());
        checkDriverHasCar(driverResponse, carResponse);
        try {
            rideDriverMapper.updateRideFromDto(rideRequest, ride, RideStatus.ACCEPTED);
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
    public RideResponse updateStatus(@Valid RideStatusRequest rideRequest,
                                     @Positive(message = "{validate.method.parameter.id.negative}") Long id) {
        Ride ride = findByIdOrThrow(id);
        checkStatusChangeRules(ride, rideRequest);
        try {
            rideStatusMapper.updateRideFromDto(rideRequest, ride);
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

    private void checkPassengerExistenceById(Long id) {
        passengerClient.getPassengerById(id);
    }

    private DriverResponse getDriverById(Long id) {
        return driverClient.getDriverById(id).getBody();
    }

    private CarResponse getCarById(Long id) {
        return driverClient.getCarById(id).getBody();
    }

    private void checkDriverHasCar(DriverResponse driverResponse, CarResponse carResponse) {
        Long carId = carResponse.id();
        if (!driverResponse.carIds().contains(carId)) {
            throw new DriverNotContainsCarException(
                    getExceptionMessage(DRIVER_NOT_CONTAINS_CAR, driverResponse.id(), carId)
            );
        }
    }

    private void checkRideHasDriver(Ride ride) {
        if (ride.getDriverId() != null) {
            throw new RideNotContainsDriverException(
                    getExceptionMessage(RIDE_CONTAINS_DRIVER, ride.getId())
            );
        }
    }

    private void checkStatusChangeRules(Ride ride, RideStatusRequest rideRequest) {
        RideStatus oldRideStatus = ride.getStatus();
        RideStatus newRideStatus = RideStatus.valueOf(rideRequest.status().toUpperCase());

        throwIfOldStatusIsCompletedOrCancelled(oldRideStatus, newRideStatus);
        throwIfWrongStatusChangeOrder(oldRideStatus, newRideStatus);
    }

    private void throwIfOldStatusIsCompletedOrCancelled(RideStatus oldRideStatus, RideStatus newRideStatus) {
        if (oldRideStatus.equals(RideStatus.COMPLETED) || oldRideStatus.equals(RideStatus.CANCELLED)) {
            throw new WrongStatusChangeException(
                    getExceptionMessage(WRONG_STATUS_CHANGE,
                            oldRideStatus.name().toLowerCase(), newRideStatus.name().toLowerCase())
            );
        }
    }

    private void throwIfWrongStatusChangeOrder(RideStatus oldRideStatus, RideStatus newRideStatus) {
        int statusDif = newRideStatus.getCode() - oldRideStatus.getCode();
        if ((statusDif < 0 || statusDif > RideStatus.ACCEPTED.getCode() - RideStatus.CREATED.getCode())
                && !newRideStatus.equals(RideStatus.COMPLETED)
                && !newRideStatus.equals(RideStatus.CANCELLED)) {
            throw new WrongStatusChangeException(getExceptionMessage(WRONG_STATUS_CHANGE,
                            oldRideStatus.name().toLowerCase(), newRideStatus.name().toLowerCase()));
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
