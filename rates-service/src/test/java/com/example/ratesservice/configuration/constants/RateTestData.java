package com.example.ratesservice.configuration.constants;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.ratesservice.dto.rate.RateAverageResponse;
import com.example.ratesservice.dto.rate.RatePageResponse;
import com.example.ratesservice.dto.rate.RateRequest;
import com.example.ratesservice.dto.rate.RateResponse;
import com.example.ratesservice.dto.rate.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.model.Rate;
import com.example.ratesservice.model.RateChangeEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RateTestData {

    public final static Long RATE_ID = 1L;
    public final static Long RATE_ID_2 = 2L;
    public final static Long INVALID_RATE_ID = -1L;
    public final static Long NOT_EXIST_RATE_ID = Long.MAX_VALUE;
    public final static Long RIDE_ID = 1L;
    public final static Long RIDE_ID_2 = 2L;
    public final static Long RIDE_ID_CREATED = 1L;
    public final static Long PASSENGER_ID = 1L;
    public final static Long DRIVER_ID = 1L;
    public final static Long EVENT_ID = 1L;

    public final static Integer VALUE = 5;
    public final static Integer VALUE_2 = 0;
    public final static Integer VALUE_CREATED = 3;
    public final static String RECIPIENT = RecipientType.DRIVER.name().toLowerCase(Locale.ROOT);
    public final static String RECIPIENT_CREATED = RecipientType.PASSENGER.name().toLowerCase(Locale.ROOT);
    public final static String COMMENT = "So cool driver!";
    public final static String COMMENT_2 = "Rude passenger...";
    public final static String COMMENT_UPDATED = "Updated comment...";

    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static double AVERAGE = 4.5;
    public final static BigDecimal EVENT_RATE = BigDecimal.valueOf(4.81);
    public final static BigDecimal AVERAGE_RATE = BigDecimal.valueOf(AVERAGE).setScale(2, RoundingMode.CEILING);

    public final static Rate RATE = Rate.builder()
            .id(RATE_ID)
            .rideId(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RecipientType.DRIVER)
            .value(VALUE)
            .comment(COMMENT)
            .build();

    public final static Rate RATE2 = Rate.builder()
            .id(RATE_ID_2)
            .rideId(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RecipientType.DRIVER)
            .value(VALUE_2)
            .comment(COMMENT_2)
            .build();

    public final static RateResponse RATE_RESPONSE = RateResponse.builder()
            .id(RATE_ID)
            .rideId(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RECIPIENT)
            .value(VALUE)
            .comment(COMMENT)
            .build();

    public final static RateResponse RATE_RESPONSE2 = RateResponse.builder()
            .id(RATE_ID_2)
            .rideId(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RECIPIENT)
            .value(VALUE_2)
            .comment(COMMENT_2)
            .build();

    public final static RateResponse RATE_RESPONSE_CREATED = RateResponse.builder()
            .rideId(RIDE_ID_CREATED)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RECIPIENT_CREATED)
            .value(VALUE_CREATED)
            .comment(COMMENT)
            .build();

    public final static RateResponse RATE_RESPONSE_UPDATED = RateResponse.builder()
            .id(RATE_ID)
            .rideId(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RECIPIENT)
            .value(VALUE)
            .comment(COMMENT_UPDATED)
            .build();

    public final static RateAverageResponse RATE_AVERAGE_RESPONSE = RateAverageResponse.builder()
            .personId(PASSENGER_ID)
            .averageValue(AVERAGE_RATE)
            .build();

    public final static RateRequest RATE_REQUEST = RateRequest.builder()
            .rideId(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RECIPIENT)
            .value(VALUE)
            .comment(COMMENT)
            .build();

    public final static RateRequest RATE_REQUEST_CREATED = RateRequest.builder()
            .rideId(RIDE_ID_CREATED)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RECIPIENT_CREATED)
            .value(VALUE_CREATED)
            .comment(COMMENT)
            .build();

    public final static RateUpdateRequest RATE_REQUEST_UPDATED = RateUpdateRequest.builder()
            .value(VALUE)
            .comment(COMMENT_UPDATED)
            .build();

    public final static RateRequest INVALID_RATE_REQUEST = RateRequest.builder()
            .rideId(RIDE_ID)
            .passengerId(PASSENGER_ID)
            .driverId(DRIVER_ID)
            .recipient(RECIPIENT)
            .build();

    public final static RateUpdateRequest INVALID_RATE_REQUEST_UPDATED = RateUpdateRequest.builder()
            .comment(COMMENT_UPDATED)
            .build();

    public final static RateChangeEvent RATE_CHANGE_EVENT = RateChangeEvent.builder()
            .eventId(EVENT_ID)
            .recipientId(DRIVER_ID)
            .recipientType(RecipientType.DRIVER)
            .rate(EVENT_RATE)
            .build();

    public final static RateChangeEventResponse RATE_CHANGE_EVENT_RESPONSE = RateChangeEventResponse.builder()
            .eventId(EVENT_ID)
            .recipientId(DRIVER_ID)
            .rate(EVENT_RATE)
            .build();

    public final static List<RateResponse> RATE_RESPONSE_LIST = List.of(
            RATE_RESPONSE,
            RATE_RESPONSE2
    );

    public final static Page<Rate> RATE_PAGE = new PageImpl<>(
            List.of(
                    RATE,
                    RATE2
            ),
            PageRequest.of(OFFSET, LIMIT),
            RATE_RESPONSE_LIST.size()
    );

    public final static RatePageResponse RATE_PAGE_RESPONSE = RatePageResponse.builder()
            .rateList(RATE_RESPONSE_LIST)
            .currentPageNumber(OFFSET)
            .pageLimit(LIMIT)
            .totalPages(RATE_RESPONSE_LIST.size() / LIMIT + 1)
            .totalElements(RATE_RESPONSE_LIST.size())
            .build();

    public final static RatePageResponse RATE_PAGE_RESPONSE_DRIVERS = RatePageResponse.builder()
            .rateList(List.of())
            .currentPageNumber(OFFSET)
            .pageLimit(LIMIT)
            .totalPages(0)
            .totalElements(0)
            .build();

}
