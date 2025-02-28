package com.example.passengerservice.controller;

import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.service.PassengerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PassengerControllerTest {

    @InjectMocks
    PassengerController passengerController;

    @Mock
    PassengerService passengerService;

    @Test
    void getPassengerTask_ReturnsValidResponseEntity() {

    }

    @Test
    void getAllPassengers() {
    }

    @Test
    void createPassenger() {
    }

    @Test
    void updatePassenger() {
    }

    @Test
    void deletePassenger() {
    }
}