package com.example.driverservice.service;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.page.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public interface CarService {

    CarResponse findById(UUID id);

    PageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    CarResponse create(@Valid CarRequest carRequest);

    CarResponse update(@Valid CarRequest carRequest, UUID id);

    void delete(UUID id);

}
