package com.example.driverservice.utility.constants;

import static com.example.driverservice.utility.constants.CarTestData.CAR_ID;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.enums.UserGender;
import com.example.driverservice.model.entity.Driver;
import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DriverTestData {

    public final static Long DRIVER_ID = 1L;
    public final static Long INVALID_DRIVER_ID = -1L;
    public final static Long NOT_EXIST_DRIVER_ID = Long.MAX_VALUE;
    public final static int OFFSET = 0;
    public final static int INVALID_OFFSET = -1;
    public final static int LIMIT = 10;
    public final static int INVALID_LIMIT = -10;
    public final static int LIMIT_CUT = LIMIT - 5;

    public final static Driver DRIVER = Driver.builder()
            .id(1L)
            .name("John Cena")
            .email("john@gmail.com")
            .phone("+375291111111")
            .gender(UserGender.MALE)
            .rate(BigDecimal.valueOf(4.52))
            .build();

    public final static Driver DRIVER2 = Driver.builder()
            .id(2L)
            .name("Leeroy Jenkins")
            .email("leeroy228@gmail.com")
            .phone("+375292222222")
            .gender(UserGender.MALE)
            .rate(BigDecimal.valueOf(4.73))
            .build();

    public final static DriverResponse DRIVER_RESPONSE = DriverResponse.builder()
            .id(1L)
            .name("John Cena")
            .email("john@gmail.com")
            .phone("+375291111111")
            .gender(UserGender.MALE.name().toLowerCase())
            .rate(BigDecimal.valueOf(4.52))
            .carIds(List.of(CAR_ID))
            .build();

    public final static DriverResponse DRIVER_RESPONSE2 = DriverResponse.builder()
            .id(2L)
            .name("Leeroy Jenkins")
            .email("leeroy228@gmail.com")
            .phone("+375292222222")
            .gender(UserGender.MALE.name().toLowerCase())
            .rate(BigDecimal.valueOf(4.73))
            .carIds(List.of(2L))
            .build();

    public final static DriverResponse DRIVER_RESPONSE_CREATED = DriverResponse.builder()
            .name("Pepe the Frog")
            .email("qwaqwa@gmail.com")
            .phone("+375293333333")
            .gender(UserGender.MALE.name().toLowerCase())
            .carIds(List.of())
            .build();

    public final static DriverResponse DRIVER_RESPONSE_UPDATED = DriverResponse.builder()
            .id(1L)
            .name("Kermit the Frog")
            .email("john@gmail.com")
            .phone("+375291111111")
            .gender(UserGender.MALE.name().toLowerCase())
            .rate(BigDecimal.valueOf(4.52))
            .carIds(List.of(CAR_ID))
            .build();

    public final static DriverRequest DRIVER_REQUEST = DriverRequest.builder()
            .name("John Cena")
            .email("john@gmail.com")
            .phone("+375291111111")
            .gender(UserGender.MALE.name().toLowerCase())
            .build();

    public final static DriverRequest DRIVER_REQUEST_CREATED = DriverRequest.builder()
            .name("Pepe the Frog")
            .email("qwaqwa@gmail.com")
            .phone("+375293333333")
            .gender(UserGender.MALE.name().toLowerCase())
            .build();

    public final static DriverRequest DRIVER_REQUEST_UPDATED = DriverRequest.builder()
            .name("Kermit the Frog")
            .email("john@gmail.com")
            .phone("+375291111111")
            .gender(UserGender.MALE.name().toLowerCase())
            .build();

    public final static DriverRequest INVALID_DRIVER_REQUEST = DriverRequest.builder()
            .name("Kermit the Frog")
            .email("john@gmail.com")
            .gender(UserGender.MALE.name().toLowerCase())
            .build();

    public final static RateChangeEventResponse RATE_CHANGE_EVENT_RESPONSE = RateChangeEventResponse.builder()
            .eventId(1L)
            .recipientId(1L)
            .rate(BigDecimal.valueOf(4.75))
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