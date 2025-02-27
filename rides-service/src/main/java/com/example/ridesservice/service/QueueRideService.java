package com.example.ridesservice.service;

import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.model.Ride;

public interface QueueRideService {

    QueueRide popRide();

    void append(Ride ride);

}
