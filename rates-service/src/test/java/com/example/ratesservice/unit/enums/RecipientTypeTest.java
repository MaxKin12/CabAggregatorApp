package com.example.ratesservice.unit.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.ratesservice.enums.RecipientType;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class RecipientTypeTest {

    @Test
    void testCodeToUserGender_ValidCode() {
        Optional<RecipientType> result = RecipientType.codeToRideStatus(RecipientType.PASSENGER.getCode());
        assertThat(result.isPresent()).isTrue();
        assertThat(RecipientType.PASSENGER).isEqualTo(result.get());

        result = RecipientType.codeToRideStatus(RecipientType.DRIVER.getCode());
        assertThat(result.isPresent()).isTrue();
        assertThat(RecipientType.DRIVER).isEqualTo(result.get());
    }

    @Test
    void testCodeToUserGender_InvalidCode() {
        Optional<RecipientType> result = RecipientType.codeToRideStatus(999);
        assertThat(result.isPresent()).isFalse();
    }

}
