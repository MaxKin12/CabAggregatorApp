package com.example.driverservice.unit.enums.converter;

import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_ARGS_FIELD;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE_KEY_FIELD;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ENUM_ARGUMENT;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.driverservice.enums.UserGender;
import com.example.driverservice.enums.converter.UserGenderConverter;
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

        assertThat(result).isEqualTo(userGender.getCode());
    }

    @Test
    void convertToEntityAttributeTest_ValidCode_ReturnsValidUserGender() {
        Integer code = 1;

        UserGender result = converter.convertToEntityAttribute(code);

        assertThat(result).isEqualTo(UserGender.MALE);
    }

    @Test
    void convertToEntityAttributeTest_InvalidCode_ThrowsException() {
        Integer invalidCode = 999;
        String[] args = new String[] {invalidCode.toString()};

        assertThatThrownBy(() -> converter.convertToEntityAttribute(invalidCode))
                .isInstanceOf(IllegalEnumArgumentException.class)
                .hasFieldOrPropertyWithValue(EXCEPTION_MESSAGE_KEY_FIELD, INVALID_ENUM_ARGUMENT)
                .hasFieldOrPropertyWithValue(EXCEPTION_ARGS_FIELD, args);
    }

}
