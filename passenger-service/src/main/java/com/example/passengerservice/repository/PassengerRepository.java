package com.example.passengerservice.repository;

import java.util.UUID;
import com.example.passengerservice.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, UUID> {
}
