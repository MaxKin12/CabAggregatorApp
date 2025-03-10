package com.example.ridesservice.constants;

import com.example.ridesservice.dto.ride.request.RideBookingRequest;
import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.model.Ride;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RideTestData {

    public final static Long RIDE_ID = 1L;
    public final static Long INVALID_RIDE_ID = -1L;
    public final static Long NOT_EXIST_RIDE_ID = Long.MAX_VALUE;
    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static Ride RIDE = Ride.builder()
            .id(1L)
            .passengerId(1L)
            .driverId(1L)
            .carId(1L)
            .pickUpAddress("Независимости 4, Минск")
            .destinationAddress("Гикало 9, Минск")
            .status(RideStatus.COMPLETED)
            .orderTime(LocalDateTime.of(2025, 2, 2, 15, 0, 0))
            .price(BigDecimal.valueOf(17.5))
            .build();

    public final static Ride RIDE2 = Ride.builder()
            .id(2L)
            .passengerId(5L)
            .driverId(5L)
            .carId(4L)
            .pickUpAddress("Притыцкого 132, Барановичи")
            .destinationAddress("Новаторов 120, Барановичи")
            .status(RideStatus.CANCELLED)
            .orderTime(LocalDateTime.of(2025, 2, 2, 16, 0, 0))
            .price(BigDecimal.valueOf(28))
            .build();

    public final static RideResponse RIDE_RESPONSE = RideResponse.builder()
            .id(1L)
            .passengerId(1L)
            .driverId(1L)
            .carId(1L)
            .pickUpAddress("Независимости 4, Минск")
            .destinationAddress("Гикало 9, Минск")
            .status("completed")
            .orderTime(LocalDateTime.of(2025, 2, 2, 15, 0, 0))
            .price(BigDecimal.valueOf(17.5))
            .build();

    public final static RideResponse RIDE_RESPONSE2 = RideResponse.builder()
            .id(2L)
            .passengerId(5L)
            .driverId(5L)
            .carId(4L)
            .pickUpAddress("Притыцкого 132, Барановичи")
            .destinationAddress("Новаторов 120, Барановичи")
            .status("canceled")
            .orderTime(LocalDateTime.of(2025, 2, 2, 15, 0, 0))
            .price(BigDecimal.valueOf(28))
            .build();

    public final static RideResponse RIDE_RESPONSE_CREATED = RideResponse.builder()
            .passengerId(3L)
            .driverId(2L)
            .carId(3L)
            .pickUpAddress("Примерный адрес 1")
            .destinationAddress("Примерный адрес 2")
            .status("created")
            .orderTime(LocalDateTime.of(2025, 3, 1, 14, 0, 0))
            .price(BigDecimal.valueOf(20))
            .build();

    public final static RideResponse RIDE_RESPONSE_UPDATED = RideResponse.builder()
            .id(1L)
            .passengerId(1L)
            .driverId(1L)
            .carId(1L)
            .pickUpAddress("Независимости 4, Минск")
            .destinationAddress("Гикало 9, Минск")
            .status("accepted")
            .orderTime(LocalDateTime.of(2025, 2, 2, 15, 0, 0))
            .price(BigDecimal.valueOf(17.5))
            .build();

    public final static RideRequest RIDE_REQUEST = RideRequest.builder()
            .passengerId(1L)
            .driverId(1L)
            .carId(1L)
            .pickUpAddress("Независимости 4, Минск")
            .destinationAddress("Гикало 9, Минск")
            .status("completed")
            .build();

    public final static RideRequest RIDE_REQUEST_CREATED = RideRequest.builder()
            .passengerId(3L)
            .driverId(2L)
            .carId(3L)
            .pickUpAddress("Примерный адрес 1")
            .destinationAddress("Примерный адрес 2")
            .status("created")
            .build();

    public final static RideRequest RIDE_REQUEST_UPDATED = RideRequest.builder()
            .passengerId(1L)
            .driverId(1L)
            .carId(1L)
            .pickUpAddress("Независимости 4, Минск")
            .destinationAddress("Гикало 9, Минск")
            .status("accepted")
            .build();

    public final static RideBookingRequest RIDE_BOOKING_REQUEST = RideBookingRequest.builder()
            .passengerId(1L)
            .pickUpAddress("Независимости 4, Минск")
            .destinationAddress("Гикало 9, Минск")
            .build();

    public final static RideDriverSettingRequest RIDE_SETTING_REQUEST = RideDriverSettingRequest.builder()
            .driverId(1L)
            .carId(1L)
            .build();

    public final static RideStatusRequest RIDE_STATUS_REQUEST = new RideStatusRequest(
            RideStatus.TAKING.name().toLowerCase());

    public final static List<RideResponse> RIDE_RESPONSE_LIST = List.of(
            RIDE_RESPONSE,
            RIDE_RESPONSE2
    );

    public final static Page<Ride> RIDE_PAGE = new PageImpl<>(
            List.of(
                    RIDE,
                    RIDE2
            ),
            PageRequest.of(OFFSET, LIMIT),
            RIDE_RESPONSE_LIST.size());

    public final static RidePageResponse RIDE_PAGE_RESPONSE = RidePageResponse.builder()
            .rideList(RIDE_RESPONSE_LIST)
            .currentPageNumber(OFFSET)
            .pageLimit(LIMIT)
            .totalPages(RIDE_RESPONSE_LIST.size() / LIMIT + 1)
            .totalElements(RIDE_RESPONSE_LIST.size())
            .build();

}
