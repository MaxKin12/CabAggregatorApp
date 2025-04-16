package com.example.ratesservice.repository;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.model.Rate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    Page<Rate> findAllByRecipient(PageRequest pageRequest, RecipientType recipientType);

    boolean existsRateByRideIdAndRecipient(Long rideId, RecipientType author);

    List<Rate> findByPassengerIdAndRecipient(PageRequest pageRequest, UUID passengerId, RecipientType recipientType);

    List<Rate> findByDriverIdAndRecipient(PageRequest pageRequest, UUID driverId, RecipientType recipientType);

}
