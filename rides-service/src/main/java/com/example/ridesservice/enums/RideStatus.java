package com.example.ridesservice.enums;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.WRONG_STATUS_TRANSITION;

import com.example.ridesservice.exception.custom.WrongStatusTransitionException;
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

    public static void throwIfWrongStatusTransition(RideStatus oldRideStatus, RideStatus newRideStatus) {
        int statusDif = RideStatus.ACCEPTED.getCode() - RideStatus.CREATED.getCode();
        int actualStatusDif = newRideStatus.getCode() - oldRideStatus.getCode();
        if (oldRideStatus.equals(RideStatus.COMPLETED)
                || ((actualStatusDif < 0 || actualStatusDif > statusDif) && !newRideStatus.equals(RideStatus.CANCELLED))
        ) {
            throw new WrongStatusTransitionException(WRONG_STATUS_TRANSITION);
        }
    }

}
