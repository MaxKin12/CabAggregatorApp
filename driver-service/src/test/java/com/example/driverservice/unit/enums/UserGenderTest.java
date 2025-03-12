package com.example.driverservice.unit.enums;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.driverservice.enums.UserGender;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class UserGenderTest {

    @Test
    void testCodeToUserGender_ValidCode() {
        Optional<UserGender> result = UserGender.codeToUserGender(0);
        assertThat(result.isPresent()).isTrue();
        assertThat(UserGender.UNKNOWN).isEqualTo(result.get());

        result = UserGender.codeToUserGender(1);
        assertThat(result.isPresent()).isTrue();
        assertThat(UserGender.MALE).isEqualTo(result.get());

        result = UserGender.codeToUserGender(2);
        assertThat(result.isPresent()).isTrue();
        assertThat(UserGender.FEMALE).isEqualTo(result.get());
    }

    @Test
    void testCodeToUserGender_InvalidCode() {
        Optional<UserGender> result = UserGender.codeToUserGender(999);
        assertThat(result.isPresent()).isFalse();
    }

}
