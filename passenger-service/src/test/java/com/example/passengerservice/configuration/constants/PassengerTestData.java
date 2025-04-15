package com.example.passengerservice.configuration.constants;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import com.example.passengerservice.model.Passenger;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerTestData {

    public final static UUID PASSENGER_ID = UUID.fromString("04f13490-2048-4b99-8514-17a4e90dc3ba");
    public final static UUID PASSENGER_ID_2 = UUID.fromString("b8090af1-42a4-4154-a23f-7e4ffa7c400f");
    public final static UUID NOT_EXIST_PASSENGER_ID = UUID.fromString("0220f106-09a1-429c-a309-e7f99cf4499a");
    public final static Long EVENT_ID = 1L;

    public final static BigDecimal PASSENGER_RATE = BigDecimal.valueOf(4.52);
    public final static BigDecimal PASSENGER_RATE_2 = BigDecimal.valueOf(4.73);
    public final static BigDecimal EVENT_RATE = BigDecimal.valueOf(4.81);

    public final static String PASSENGER_NAME = "John Cena";
    public final static String PASSENGER_NAME_2 = "Leeroy Jenkins";
    public final static String PASSENGER_CREATED_NAME = "Pepe the Frog";
    public final static String PASSENGER_UPDATED_NAME = "Kermit the Frog";
    public final static String PASSENGER_EMAIL = "john@gmail.com";
    public final static String PASSENGER_EMAIL_2 = "leeroy228@gmail.com";
    public final static String PASSENGER_CREATED_EMAIL = "qwaqwa@gmail.com";
    public final static String PASSENGER_PHONE = "+375291111111";
    public final static String PASSENGER_PHONE_2 = "+375292222222";
    public final static String PASSENGER_CREATED_PHONE = "+375293333333";

    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static Passenger PASSENGER = Passenger.builder()
            .id(PASSENGER_ID)
            .name(PASSENGER_NAME)
            .email(PASSENGER_EMAIL)
            .phone(PASSENGER_PHONE)
            .rate(PASSENGER_RATE)
            .build();

    public final static Passenger PASSENGER2 = Passenger.builder()
            .id(PASSENGER_ID_2)
            .name(PASSENGER_NAME_2)
            .email(PASSENGER_EMAIL_2)
            .phone(PASSENGER_PHONE_2)
            .rate(PASSENGER_RATE_2)
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE = PassengerResponse.builder()
            .id(PASSENGER_ID)
            .name(PASSENGER_NAME)
            .email(PASSENGER_EMAIL)
            .phone(PASSENGER_PHONE)
            .rate(PASSENGER_RATE)
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE2 = PassengerResponse.builder()
            .id(PASSENGER_ID_2)
            .name(PASSENGER_NAME_2)
            .email(PASSENGER_EMAIL_2)
            .phone(PASSENGER_PHONE_2)
            .rate(PASSENGER_RATE_2)
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE_CREATED = PassengerResponse.builder()
            .name(PASSENGER_CREATED_NAME)
            .email(PASSENGER_CREATED_EMAIL)
            .phone(PASSENGER_CREATED_PHONE)
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE_UPDATED = PassengerResponse.builder()
            .id(PASSENGER_ID)
            .name(PASSENGER_UPDATED_NAME)
            .email(PASSENGER_EMAIL)
            .phone(PASSENGER_PHONE)
            .rate(PASSENGER_RATE)
            .build();

    public final static PassengerRequest PASSENGER_REQUEST = PassengerRequest.builder()
            .name(PASSENGER_NAME)
            .email(PASSENGER_EMAIL)
            .phone(PASSENGER_PHONE)
            .build();
    public final static PassengerRequest PASSENGER_REQUEST_CREATED = PassengerRequest.builder()
            .name(PASSENGER_CREATED_NAME)
            .email(PASSENGER_CREATED_EMAIL)
            .phone(PASSENGER_CREATED_PHONE)
            .build();

    public final static PassengerRequest PASSENGER_REQUEST_UPDATED = PassengerRequest.builder()
            .name(PASSENGER_UPDATED_NAME)
            .email(PASSENGER_EMAIL)
            .phone(PASSENGER_PHONE)
            .build();

    public final static PassengerRequest INVALID_PASSENGER_REQUEST = PassengerRequest.builder()
            .name(PASSENGER_UPDATED_NAME)
            .email(PASSENGER_EMAIL)
            .build();

    public final static RateChangeEventResponse RATE_CHANGE_EVENT_RESPONSE = RateChangeEventResponse.builder()
            .eventId(EVENT_ID)
            .recipientId(PASSENGER_ID)
            .rate(EVENT_RATE)
            .build();

    public final static List<PassengerResponse> PASSENGER_RESPONSE_LIST = List.of(
            PASSENGER_RESPONSE,
            PASSENGER_RESPONSE2
    );

    public final static Page<Passenger> PASSENGER_PAGE = new PageImpl<>(
            List.of(
                    PASSENGER,
                    PASSENGER2
            ),
            PageRequest.of(OFFSET, LIMIT),
            PASSENGER_RESPONSE_LIST.size());

    public final static PassengerPageResponse PASSENGER_PAGE_RESPONSE = PassengerPageResponse.builder()
            .passengerList(PASSENGER_RESPONSE_LIST)
            .currentPageNumber(OFFSET)
            .pageLimit(LIMIT)
            .totalPages(PASSENGER_RESPONSE_LIST.size() / LIMIT + 1)
            .totalElements(PASSENGER_RESPONSE_LIST.size())
            .build();

}
