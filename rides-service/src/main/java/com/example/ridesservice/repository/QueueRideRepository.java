package com.example.ridesservice.repository;

import com.example.ridesservice.model.QueueRide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRideRepository extends JpaRepository<QueueRide, Long> {

    QueueRide findTop1ByOrderByChangedAt();

}
