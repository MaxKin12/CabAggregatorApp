package com.example.ratesservice.repository;

import com.example.ratesservice.enums.AuthorType;
import com.example.ratesservice.model.Rate;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends JpaRepository<Rate, Long> {

    boolean existsRateByRideIdAndAuthor(Long rideId, AuthorType author);

    List<Rate> findByPassengerIdAndAuthor(PageRequest pageRequest, Long passengerId, AuthorType authorType);

    List<Rate> findByDriverIdAndAuthor(PageRequest pageRequest, Long driverId, AuthorType authorType);

}
