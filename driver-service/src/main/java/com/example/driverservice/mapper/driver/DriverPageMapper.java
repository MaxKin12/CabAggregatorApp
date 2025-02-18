package com.example.driverservice.mapper.driver;

import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.mapper.PageMapper;
import com.example.driverservice.model.entity.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = DriverMapper.class
)
public interface DriverPageMapper extends PageMapper<DriverResponse, Driver> {
}
