package com.example.driverservice.mapper.driver;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.enums.UserGender;
import com.example.driverservice.model.entity.Car;
import com.example.driverservice.model.entity.Driver;
import java.util.ArrayList;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DriverMapper {

    @Mapping(target = "carIds", source = "cars", qualifiedByName = "carsToCarIds")
    @Mapping(target = "gender", source = "gender", qualifiedByName = "genderToLowerCaseString")
    DriverResponse toResponse(Driver driver);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToUpperCaseGender")
    Driver toDriver(DriverRequest driverRequest);

    @Mapping(target = "gender", source = "gender", qualifiedByName = "stringToUpperCaseGender")
    void updateDriverFromDto(DriverRequest driverRequest, @MappingTarget Driver driver);

    @Named("carsToCarIds")
    default List<Long> carsToCarIds(List<Car> carList) {
        if (carList == null) {
            return new ArrayList<>();
        }
        return carList.stream().map(Car::getId).toList();
    }

    @Named("genderToLowerCaseString")
    default String genderToLowerCaseString(UserGender userGender) {
        return userGender.name().toLowerCase();
    }

    @Named("stringToUpperCaseGender")
    default UserGender stringToUpperCaseGender(String gender) {
        return UserGender.valueOf(gender.toUpperCase());
    }

}
