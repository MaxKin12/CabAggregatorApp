package com.example.driverservice.enums;

import java.util.Arrays;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum UserGender {
    UNKNOWN(0),
    MALE(1),
    FEMALE(2);

    private final int code;

    public static Optional<UserGender> codeToUserGender(Integer code) {
        return Arrays.stream(UserGender.values())
                .filter(p -> p.getCode() == code)
                .findAny();
    }
}
