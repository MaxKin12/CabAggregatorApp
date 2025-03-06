package com.example.driverservice.enums;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

public class UserGenderTest {

    @Test
    void testCodeToUserGender_ValidCode() {
        Optional<UserGender> result = UserGender.codeToUserGender(0);
        assertTrue(result.isPresent());
        assertEquals(UserGender.UNKNOWN, result.get());

        result = UserGender.codeToUserGender(1);
        assertTrue(result.isPresent());
        assertEquals(UserGender.MALE, result.get());

        result = UserGender.codeToUserGender(2);
        assertTrue(result.isPresent());
        assertEquals(UserGender.FEMALE, result.get());
    }

    @Test
    void testCodeToUserGender_InvalidCode() {
        Optional<UserGender> result = UserGender.codeToUserGender(999);
        assertFalse(result.isPresent());
    }

}
