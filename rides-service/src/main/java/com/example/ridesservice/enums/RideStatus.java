package com.example.ridesservice.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RideStatus {

    UNKNOWN(0),
    CREATED(100),
    ACCEPTED(200),
    TAKING(300),
    DELIVERING(400),
    COMPLETED(500),
    CANCELLED(600);

    private final int code;

    public static Optional<RideStatus> codeToRideStatus(Integer code) {
        return Arrays.stream(RideStatus.values())
                .filter(p -> p.getCode() == code)
                .findAny();
    }

}
