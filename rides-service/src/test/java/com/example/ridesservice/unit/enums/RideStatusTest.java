package com.example.ridesservice.unit.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import com.example.ridesservice.enums.RideStatus;
import org.junit.jupiter.api.Test;

public class RideStatusTest {

    @Test
    void testCodeToUserGender_ValidCode() {
        Optional<RideStatus> result = RideStatus.codeToRideStatus(RideStatus.UNKNOWN.getCode());
        assertThat(result.isPresent()).isTrue();
        assertThat(RideStatus.UNKNOWN).isEqualTo(result.get());

        result = RideStatus.codeToRideStatus(RideStatus.CANCELLED.getCode());
        assertThat(result.isPresent()).isTrue();
        assertThat(RideStatus.CANCELLED).isEqualTo(result.get());
    }

    @Test
    void testCodeToUserGender_InvalidCode() {
        Optional<RideStatus> result = RideStatus.codeToRideStatus(999);
        assertThat(result.isPresent()).isFalse();
    }

}
