package com.example.driverservice.utility.constants;

import static com.example.driverservice.utility.constants.DriverTestData.DRIVER;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER2;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_ID;

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
    public final static Long INVALID_CAR_ID = -1L;
    public final static Long NOT_EXIST_CAR_ID = Long.MAX_VALUE;
    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static Car CAR = Car.builder()
            .id(1L)
            .brand("Toyota")
            .number("1234AB-3")
            .color("Red")
            .driver(DRIVER)
            .build();

    public final static Car CAR2 = Car.builder()
            .id(2L)
            .brand("Ford")
            .number("1234AB-5")
            .color("Blue")
            .driver(DRIVER2)
            .build();

    public final static CarResponse CAR_RESPONSE = CarResponse.builder()
            .id(1L)
            .brand("Toyota")
            .number("1234AB-3")
            .color("Red")
            .driverId(DRIVER_ID)
            .build();

    public final static CarResponse CAR_RESPONSE2 = CarResponse.builder()
            .id(2L)
            .brand("Ford")
            .number("1234AB-5")
            .color("Blue")
            .driverId(2L)
            .build();

    public final static CarResponse CAR_RESPONSE_CREATED = CarResponse.builder()
            .brand("Honda")
            .number("1234AB-7")
            .color("Green")
            .driverId(2L)
            .build();

    public final static CarResponse CAR_RESPONSE_UPDATED = CarResponse.builder()
            .id(1L)
            .brand("Toyota")
            .number("1234AB-3")
            .color("Grey")
            .driverId(DRIVER_ID)
            .build();

    public final static CarRequest CAR_REQUEST = CarRequest.builder()
            .brand("Toyota")
            .number("1234AB-3")
            .color("Red")
            .driverId(DRIVER_ID)
            .build();

    public final static CarRequest CAR_REQUEST_CREATED = CarRequest.builder()
            .brand("Honda")
            .number("1234AB-7")
            .color("Green")
            .driverId(2L)
            .build();

    public final static CarRequest CAR_REQUEST_UPDATED = CarRequest.builder()
            .brand("Toyota")
            .number("1234AB-3")
            .color("Grey")
            .driverId(DRIVER_ID)
            .build();

    public final static CarRequest INVALID_CAR_REQUEST = CarRequest.builder()
            .brand("Toyota")
            .color("Red")
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
