package com.example.ridesservice.configuration.constants;

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
    public final static Long RIDE_ID_2 = 2L;
    public final static Long INVALID_RIDE_ID = -1L;
    public final static Long NOT_EXIST_RIDE_ID = Long.MAX_VALUE;
    public final static Long PASSENGER_ID = 1L;
    public final static Long PASSENGER_ID_2 = 2L;
    public final static Long INVALID_PASSENGER_ID = -1L;
    public final static Long DRIVER_ID = 1L;
    public final static Long DRIVER_ID_2 = 2L;
    public final static Long CAR_ID = 1L;
    public final static Long CAR_ID_2 = 2L;

    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static String PICK_UP_ADDRESS = "Независимости 4, Минск";
    public final static String PICK_UP_ADDRESS_2 = "Притыцкого 132, Барановичи";
    public final static String DESTINATION_ADDRESS = "Гикало 9, Минск";
    public final static String DESTINATION_ADDRESS_2 = "Новаторов 120, Барановичи";

    public final static String RIDE_STATUS_COMPLETED = "completed";
    public final static String RIDE_STATUS_CANCELLED = "cancelled";
    public final static String RIDE_STATUS_CREATED = "created";
    public final static String RIDE_STATUS_ACCEPTED = "accepted";
    public final static String RIDE_STATUS_TAKING = "taking";

    public final static BigDecimal PRICE = BigDecimal.valueOf(17.5);
    public final static BigDecimal PRICE_2 = BigDecimal.valueOf(28);
    public final static LocalDateTime ORDER_TIME = LocalDateTime.of(2025, 2, 2, 15, 0, 0);
    public final static LocalDateTime ORDER_TIME_2 = LocalDateTime.of(2025, 2, 2, 16, 0, 0);

    public final static Ride RIDE = Ride.builder()
            .id(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RideStatus.COMPLETED)
            .orderTime(ORDER_TIME)
            .price(PRICE)
            .build();

    public final static Ride RIDE2 = Ride.builder()
            .id(RIDE_ID_2)
            .passengerId(PASSENGER_ID_2)
            .driverId(DRIVER_ID_2)
            .carId(CAR_ID_2)
            .pickUpAddress(PICK_UP_ADDRESS_2)
            .destinationAddress(DESTINATION_ADDRESS_2)
            .status(RideStatus.CANCELLED)
            .orderTime(ORDER_TIME_2)
            .price(PRICE_2)
            .build();

    public final static RideResponse RIDE_RESPONSE = RideResponse.builder()
            .id(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RIDE_STATUS_COMPLETED)
            .orderTime(ORDER_TIME)
            .price(PRICE)
            .build();

    public final static RideResponse RIDE_RESPONSE2 = RideResponse.builder()
            .id(RIDE_ID_2)
            .passengerId(PASSENGER_ID_2)
            .driverId(DRIVER_ID_2)
            .carId(CAR_ID_2)
            .pickUpAddress(PICK_UP_ADDRESS_2)
            .destinationAddress(DESTINATION_ADDRESS_2)
            .status(RIDE_STATUS_CANCELLED)
            .orderTime(ORDER_TIME_2)
            .price(PRICE_2)
            .build();

    public final static RideResponse RIDE_RESPONSE_CREATED = RideResponse.builder()
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RIDE_STATUS_CREATED)
            .orderTime(ORDER_TIME)
            .price(PRICE)
            .build();

    public final static RideResponse RIDE_RESPONSE_UPDATED = RideResponse.builder()
            .id(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RIDE_STATUS_ACCEPTED)
            .orderTime(ORDER_TIME)
            .price(PRICE)
            .build();

    public final static RideRequest RIDE_REQUEST = RideRequest.builder()
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RIDE_STATUS_COMPLETED)
            .build();

    public final static RideRequest RIDE_REQUEST_CREATED = RideRequest.builder()
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RIDE_STATUS_CREATED)
            .build();

    public final static RideRequest RIDE_REQUEST_UPDATED = RideRequest.builder()
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RIDE_STATUS_ACCEPTED)
            .build();

    public final static RideRequest INVALID_RIDE_REQUEST = RideRequest.builder()
            .passengerId(INVALID_PASSENGER_ID)
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .status(RIDE_STATUS_ACCEPTED)
            .build();

    public final static RideBookingRequest RIDE_BOOKING_REQUEST = RideBookingRequest.builder()
            .passengerId(PASSENGER_ID)
            .pickUpAddress(PICK_UP_ADDRESS)
            .destinationAddress(DESTINATION_ADDRESS)
            .build();

    public final static RideDriverSettingRequest RIDE_SETTING_REQUEST = RideDriverSettingRequest.builder()
            .driverId(DRIVER_ID)
            .carId(CAR_ID)
            .build();

    public final static RideStatusRequest RIDE_STATUS_REQUEST = new RideStatusRequest(RIDE_STATUS_TAKING);

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
