package com.example.driverservice.enums.converter;

import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ENUM_ARGUMENT;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.driverservice.enums.UserGender;
import com.example.driverservice.exception.custom.IllegalEnumArgumentException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserGenderConverterTest {

    @InjectMocks
    private UserGenderConverter converter;

    @Test
    void convertToDatabaseColumn_ReturnsValidInteger() {
        UserGender userGender = UserGender.MALE;

        Integer result = converter.convertToDatabaseColumn(userGender);

        assertEquals(userGender.getCode(), result);
    }

    @Test
    void convertToEntityAttributeTest_ValidCode_ReturnsValidUserGender() {
        Integer code = 1;

        UserGender result = converter.convertToEntityAttribute(code);

        assertEquals(UserGender.MALE, result);
    }

    @Test
    void convertToEntityAttributeTest_InvalidCode_ThrowsException() {
        Integer invalidCode = 999;
        String[] args = new String[] {invalidCode.toString()};

        IllegalEnumArgumentException exception = assertThrows(IllegalEnumArgumentException.class,
                () -> converter.convertToEntityAttribute(invalidCode));

        assertEquals(INVALID_ENUM_ARGUMENT, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
    }

}
