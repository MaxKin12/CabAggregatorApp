package com.example.ridesservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RideStatus {
    UNKNOWN((byte) 0),
    CREATED((byte) 1),
    ACCEPTED((byte) 2),
    TAKING((byte) 3),
    DELIVERING((byte) 4),
    COMPLETED((byte) 5),
    CANCELLED((byte) 6);

    private final byte code;
}
