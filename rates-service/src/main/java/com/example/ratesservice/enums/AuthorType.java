package com.example.ratesservice.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum AuthorType {

    PASSENGER(0),
    DRIVER(1);

    private final int code;

    public static Optional<AuthorType> codeToRideStatus(Integer code) {
        return Arrays.stream(AuthorType.values())
                .filter(p -> p.getCode() == code)
                .findAny();
    }

}
