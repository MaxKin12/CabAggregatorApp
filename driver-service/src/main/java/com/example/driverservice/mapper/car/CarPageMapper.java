package com.example.driverservice.mapper.car;

import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.mapper.PageMapper;
import com.example.driverservice.model.entity.Car;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = CarMapper.class
)
public interface CarPageMapper extends PageMapper<CarResponse, Car> {
}
