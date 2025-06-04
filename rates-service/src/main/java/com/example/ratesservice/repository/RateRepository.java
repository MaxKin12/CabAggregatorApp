package com.example.ratesservice.repository;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.model.Rate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RateRepository extends MongoRepository<Rate, UUID> {

    Page<Rate> findAllByRecipient(PageRequest pageRequest, RecipientType recipientType);

    boolean existsRateByRideIdAndRecipient(UUID rideId, RecipientType author);

    List<Rate> findByPassengerIdAndRecipient(PageRequest pageRequest, UUID passengerId, RecipientType recipientType);

    List<Rate> findByDriverIdAndRecipient(PageRequest pageRequest, UUID driverId, RecipientType recipientType);

}
