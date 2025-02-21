package com.example.ratesservice.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RecipientType {

    PASSENGER(0),
    DRIVER(1);

    private final int code;

    public static Optional<RecipientType> codeToRideStatus(Integer code) {
        return Arrays.stream(RecipientType.values())
                .filter(p -> p.getCode() == code)
                .findAny();
    }

}
