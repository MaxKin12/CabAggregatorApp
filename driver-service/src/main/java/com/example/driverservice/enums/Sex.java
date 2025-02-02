package com.example.driverservice.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Sex {
    UNKNOWN((byte) 0),
    MALE((byte) 1),
    FEMALE((byte) 2);

    private final byte code;
}
