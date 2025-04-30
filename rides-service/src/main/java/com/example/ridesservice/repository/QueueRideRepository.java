package com.example.ridesservice.repository;

import com.example.ridesservice.model.QueueRide;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRideRepository extends JpaRepository<QueueRide, UUID> {

    Optional<QueueRide> findTop1ByOrderByChangedAt();

}
