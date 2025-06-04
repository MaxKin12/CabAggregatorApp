package com.example.ridesservice.repository;

import java.util.UUID;
import com.example.ridesservice.model.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends JpaRepository<Ride, UUID> {

    Page<Ride> findByPassengerId(PageRequest pageRequest, UUID passengerId);

    Page<Ride> findByDriverId(PageRequest pageRequest, UUID passengerId);

}
