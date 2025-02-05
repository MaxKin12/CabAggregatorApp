package com.example.ridesservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum RideStatus {
    UNKNOWN(0),
    CREATED(1),
    ACCEPTED(2),
    TAKING(3),
    DELIVERING(4),
    COMPLETED(5),
    CANCELLED(6);

    private final int code;

    public static Optional<RideStatus> codeToRideStatus(Integer code) {
        return Arrays.stream(RideStatus.values())
                .filter(p -> p.getCode() == code)
                .findAny();
    }
}
