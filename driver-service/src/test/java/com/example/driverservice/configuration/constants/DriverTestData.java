package com.example.driverservice.configuration.constants;

import static com.example.driverservice.configuration.constants.CarTestData.CAR_ID;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_ID_2;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.enums.UserGender;
import com.example.driverservice.model.entity.Driver;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverTestData {

    public final static UUID DRIVER_ID = UUID.fromString("1377f946-6c0a-45eb-88a6-e9131c3c27ff");
    public final static UUID DRIVER_ID_2 = UUID.fromString("7757fc43-a2c4-4504-b464-bf61b293af72");
    public final static UUID NOT_EXIST_DRIVER_ID = UUID.fromString("8ec2cea3-554e-4133-9d18-2aed95fe23c5");
    public final static UUID EVENT_ID = UUID.randomUUID();

    public final static BigDecimal DRIVER_RATE = BigDecimal.valueOf(4.52);
    public final static BigDecimal DRIVER_RATE_2 = BigDecimal.valueOf(4.73);
    public final static BigDecimal EVENT_RATE = BigDecimal.valueOf(4.81);

    public final static String DRIVER_NAME = "John Cena";
    public final static String DRIVER_NAME_2 = "Leeroy Jenkins";
    public final static String DRIVER_NAME_CREATED = "Pepe the Frog";
    public final static String DRIVER_NAME_UPDATED = "Kermit the Frog";
    public final static String DRIVER_EMAIL = "john@gmail.com";
    public final static String DRIVER_EMAIL_2 = "leeroy228@gmail.com";
    public final static String DRIVER_EMAIL_CREATED = "qwaqwa@gmail.com";
    public final static String DRIVER_PHONE = "+375291111111";
    public final static String DRIVER_PHONE_2 = "+375292222222";
    public final static String DRIVER_PHONE_CREATED = "+375293333333";
    public final static String DRIVER_GENDER = UserGender.MALE.name().toLowerCase();

    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static Driver DRIVER = Driver.builder()
            .id(DRIVER_ID)
            .name(DRIVER_NAME)
            .email(DRIVER_EMAIL)
            .phone(DRIVER_PHONE)
            .gender(UserGender.MALE)
            .rate(DRIVER_RATE)
            .build();

    public final static Driver DRIVER2 = Driver.builder()
            .id(DRIVER_ID_2)
            .name(DRIVER_NAME_2)
            .email(DRIVER_EMAIL_2)
            .phone(DRIVER_PHONE_2)
            .gender(UserGender.MALE)
            .rate(DRIVER_RATE_2)
            .build();

    public final static DriverResponse DRIVER_RESPONSE = DriverResponse.builder()
            .id(DRIVER_ID)
            .name(DRIVER_NAME)
            .email(DRIVER_EMAIL)
            .phone(DRIVER_PHONE)
            .gender(DRIVER_GENDER)
            .rate(DRIVER_RATE)
            .carIds(List.of(CAR_ID))
            .build();

    public final static DriverResponse DRIVER_RESPONSE2 = DriverResponse.builder()
            .id(DRIVER_ID_2)
            .name(DRIVER_NAME_2)
            .email(DRIVER_EMAIL_2)
            .phone(DRIVER_PHONE_2)
            .gender(DRIVER_GENDER)
            .rate(DRIVER_RATE_2)
            .carIds(List.of(CAR_ID_2))
            .build();

    public final static DriverResponse DRIVER_RESPONSE_CREATED = DriverResponse.builder()
            .name(DRIVER_NAME_CREATED)
            .email(DRIVER_EMAIL_CREATED)
            .phone(DRIVER_PHONE_CREATED)
            .gender(DRIVER_GENDER)
            .carIds(List.of())
            .build();

    public final static DriverResponse DRIVER_RESPONSE_UPDATED = DriverResponse.builder()
            .id(DRIVER_ID)
            .name(DRIVER_NAME_UPDATED)
            .email(DRIVER_EMAIL)
            .phone(DRIVER_PHONE)
            .gender(DRIVER_GENDER)
            .rate(DRIVER_RATE)
            .carIds(List.of(CAR_ID))
            .build();

    public final static DriverRequest DRIVER_REQUEST = DriverRequest.builder()
            .name(DRIVER_NAME)
            .email(DRIVER_EMAIL)
            .phone(DRIVER_PHONE)
            .gender(DRIVER_GENDER)
            .build();

    public final static DriverRequest DRIVER_REQUEST_CREATED = DriverRequest.builder()
            .name(DRIVER_NAME_CREATED)
            .email(DRIVER_EMAIL_CREATED)
            .phone(DRIVER_PHONE_CREATED)
            .gender(DRIVER_GENDER)
            .build();

    public final static DriverRequest DRIVER_REQUEST_UPDATED = DriverRequest.builder()
            .name(DRIVER_NAME_UPDATED)
            .email(DRIVER_EMAIL)
            .phone(DRIVER_PHONE)
            .gender(DRIVER_GENDER)
            .build();

    public final static DriverRequest INVALID_DRIVER_REQUEST = DriverRequest.builder()
            .name(DRIVER_NAME_UPDATED)
            .email(DRIVER_EMAIL)
            .gender(DRIVER_GENDER)
            .build();

    public final static RateChangeEventResponse RATE_CHANGE_EVENT_RESPONSE = RateChangeEventResponse.builder()
            .eventId(EVENT_ID)
            .recipientId(DRIVER_ID)
            .rate(EVENT_RATE)
            .build();

    public final static List<DriverResponse> DRIVER_RESPONSE_LIST = List.of(
            DRIVER_RESPONSE,
            DRIVER_RESPONSE2
    );

    public final static Page<Driver> DRIVER_PAGE = new PageImpl<>(
            List.of(
                    DRIVER,
                    DRIVER2
            ),
            PageRequest.of(OFFSET, LIMIT),
            DRIVER_RESPONSE_LIST.size());

    public final static PageResponse DRIVER_PAGE_RESPONSE = PageResponse.builder()
            .list(DRIVER_RESPONSE_LIST)
            .currentPageNumber(OFFSET)
            .pageLimit(LIMIT)
            .totalPages(DRIVER_RESPONSE_LIST.size() / LIMIT + 1)
            .totalElements(DRIVER_RESPONSE_LIST.size())
            .build();

}
