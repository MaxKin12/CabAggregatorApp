package com.example.driverservice.repository;

import com.example.driverservice.model.entity.Driver;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
}
