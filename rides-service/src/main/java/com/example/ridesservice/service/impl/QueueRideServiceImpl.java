package com.example.ridesservice.service.impl;

import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.QueueRideRepository;
import com.example.ridesservice.service.QueueRideService;
import com.example.ridesservice.utility.validation.QueueRideServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class QueueRideServiceImpl implements QueueRideService {

    private final QueueRideServiceValidation validation;
    private final QueueRideRepository queueRideRepository;

    @Override
    @Transactional
    public QueueRide popRide() {
        QueueRide queueRide = validation.findQueueOldestRecord();
        queueRideRepository.delete(queueRide);
        return queueRide;
    }

    @Override
    @Transactional
    public void append(Ride ride) {
        QueueRide queueRide = QueueRide
                .builder()
                .rideId(ride.getId())
                .build();
        validation.saveOrThrow(queueRide);
    }

}
