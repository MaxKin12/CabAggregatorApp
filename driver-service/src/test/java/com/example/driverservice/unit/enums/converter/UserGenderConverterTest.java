package com.example.driverservice.unit.enums.converter;

import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.INVALID_ENUM_CODE;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ENUM_ARGUMENT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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
        UserGender result = converter.convertToEntityAttribute(UserGender.MALE.getCode());

        assertThat(result).isEqualTo(UserGender.MALE);
    }

    @Test
    void convertToEntityAttributeTest_InvalidCode_ThrowsException() {
        Integer invalidCode = INVALID_ENUM_CODE;
        String[] args = new String[]{invalidCode.toString()};

        assertThatExceptionOfType(IllegalEnumArgumentException.class)
                .isThrownBy(() -> converter.convertToEntityAttribute(invalidCode))
                .withMessage(INVALID_ENUM_ARGUMENT)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));
    }

}
