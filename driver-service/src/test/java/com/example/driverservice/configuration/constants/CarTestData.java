package com.example.driverservice.configuration.constants;

import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER2;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_ID_2;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.model.entity.Car;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CarTestData {

    public final static Long CAR_ID = 1L;
    public final static Long CAR_ID_2 = 2L;
    public final static Long INVALID_CAR_ID = -1L;
    public final static Long NOT_EXIST_CAR_ID = Long.MAX_VALUE;

    public final static String CAR_BRAND = "Toyota";
    public final static String CAR_BRAND_2 = "Ford";
    public final static String CAR_BRAND_CREATED = "Honda";
    public final static String CAR_NUMBER = "1234AB-3";
    public final static String CAR_NUMBER_2 = "1234AB-5";
    public final static String CAR_NUMBER_CREATED = "1234AB-7";
    public final static String CAR_COLOR = "Red";
    public final static String CAR_COLOR_2 = "Blue";
    public final static String CAR_COLOR_CREATED = "Green";
    public final static String CAR_COLOR_UPDATED = "Grey";

    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static Car CAR = Car.builder()
            .id(CAR_ID)
            .brand(CAR_BRAND)
            .number(CAR_NUMBER)
            .color(CAR_COLOR)
            .driver(DRIVER)
            .build();

    public final static Car CAR2 = Car.builder()
            .id(CAR_ID_2)
            .brand(CAR_BRAND_2)
            .number(CAR_NUMBER_2)
            .color(CAR_COLOR_2)
            .driver(DRIVER2)
            .build();

    public final static CarResponse CAR_RESPONSE = CarResponse.builder()
            .id(CAR_ID)
            .brand(CAR_BRAND)
            .number(CAR_NUMBER)
            .color(CAR_COLOR)
            .driverId(DRIVER_ID)
            .build();

    public final static CarResponse CAR_RESPONSE2 = CarResponse.builder()
            .id(CAR_ID_2)
            .brand(CAR_BRAND_2)
            .number(CAR_NUMBER_2)
            .color(CAR_COLOR_2)
            .driverId(DRIVER_ID_2)
            .build();

    public final static CarResponse CAR_RESPONSE_CREATED = CarResponse.builder()
            .brand(CAR_BRAND_CREATED)
            .number(CAR_NUMBER_CREATED)
            .color(CAR_COLOR_CREATED)
            .driverId(DRIVER_ID_2)
            .build();

    public final static CarResponse CAR_RESPONSE_UPDATED = CarResponse.builder()
            .id(CAR_ID)
            .brand(CAR_BRAND)
            .number(CAR_NUMBER)
            .color(CAR_COLOR_UPDATED)
            .driverId(DRIVER_ID)
            .build();

    public final static CarRequest CAR_REQUEST = CarRequest.builder()
            .brand(CAR_BRAND)
            .number(CAR_NUMBER)
            .color(CAR_COLOR)
            .driverId(DRIVER_ID)
            .build();

    public final static CarRequest CAR_REQUEST_CREATED = CarRequest.builder()
            .brand(CAR_BRAND_CREATED)
            .number(CAR_NUMBER_CREATED)
            .color(CAR_COLOR_CREATED)
            .driverId(DRIVER_ID_2)
            .build();

    public final static CarRequest CAR_REQUEST_UPDATED = CarRequest.builder()
            .brand(CAR_BRAND)
            .number(CAR_NUMBER)
            .color(CAR_COLOR_UPDATED)
            .driverId(DRIVER_ID)
            .build();

    public final static CarRequest INVALID_CAR_REQUEST = CarRequest.builder()
            .brand(CAR_BRAND)
            .color(CAR_COLOR)
            .build();

    public final static List<CarResponse> CAR_RESPONSE_LIST = List.of(
            CAR_RESPONSE,
            CAR_RESPONSE2
    );

    public final static Page<Car> CAR_PAGE = new PageImpl<>(
            List.of(
                    CAR,
                    CAR2
            ),
            PageRequest.of(OFFSET, LIMIT),
            CAR_RESPONSE_LIST.size());

    public final static PageResponse CAR_PAGE_RESPONSE = PageResponse.builder()
            .list(CAR_RESPONSE_LIST)
            .currentPageNumber(OFFSET)
            .pageLimit(LIMIT)
            .totalPages(CAR_RESPONSE_LIST.size() / LIMIT + 1)
            .totalElements(CAR_RESPONSE_LIST.size())
            .build();

}
