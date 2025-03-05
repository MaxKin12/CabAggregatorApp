package com.example.passengerservice.utility.constants;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import com.example.passengerservice.model.Passenger;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PassengerTestData {

    public final static Long PASSENGER_ID = 1L;
    public final static Long INVALID_PASSENGER_ID = -1L;
    public final static Long NOT_EXIST_PASSENGER_ID = Long.MAX_VALUE;
    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static Passenger PASSENGER = Passenger.builder()
            .id(1L)
            .name("John Cena")
            .email("john@gmail.com")
            .phone("+375291111111")
            .rate(BigDecimal.valueOf(4.52))
            .build();

    public final static Passenger PASSENGER2 = Passenger.builder()
            .id(2L)
            .name("Leeroy Jenkins")
            .email("leeroy228@gmail.com")
            .phone("+375292222222")
            .rate(BigDecimal.valueOf(4.73))
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE = PassengerResponse.builder()
            .id(1L)
            .name("John Cena")
            .email("john@gmail.com")
            .phone("+375291111111")
            .rate(BigDecimal.valueOf(4.52))
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE2 = PassengerResponse.builder()
            .id(2L)
            .name("Leeroy Jenkins")
            .email("leeroy228@gmail.com")
            .phone("+375292222222")
            .rate(BigDecimal.valueOf(4.73))
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE_CREATED = PassengerResponse.builder()
            .name("Pepe the Frog")
            .email("qwaqwa@gmail.com")
            .phone("+375293333333")
            .build();

    public final static PassengerResponse PASSENGER_RESPONSE_UPDATED = PassengerResponse.builder()
            .id(1L)
            .name("Kermit the Frog")
            .email("john@gmail.com")
            .phone("+375291111111")
            .rate(BigDecimal.valueOf(4.52))
            .build();

    public final static PassengerRequest PASSENGER_REQUEST = PassengerRequest.builder()
            .name("John Cena")
            .email("john@gmail.com")
            .phone("+375291111111")
            .build();
    public final static PassengerRequest PASSENGER_REQUEST_CREATED = PassengerRequest.builder()
            .name("Pepe the Frog")
            .email("qwaqwa@gmail.com")
            .phone("+375293333333")
            .build();

    public final static PassengerRequest PASSENGER_REQUEST_UPDATED = PassengerRequest.builder()
            .name("Kermit the Frog")
            .email("john@gmail.com")
            .phone("+375291111111")
            .build();

    public final static PassengerRequest INVALID_PASSENGER_REQUEST = PassengerRequest.builder()
            .name("Kermit the Frog")
            .email("john@gmail.com")
            .build();

    public final static RateChangeEventResponse RATE_CHANGE_EVENT_RESPONSE = RateChangeEventResponse.builder()
            .eventId(1L)
            .recipientId(1L)
            .rate(BigDecimal.valueOf(4.75))
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
