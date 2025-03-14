package com.example.ridesservice.unit.enums.converter;

import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.INVALID_ENUM_CODE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ENUM_ARGUMENT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.enums.converter.StatusConverter;
import com.example.ridesservice.exception.custom.IllegalEnumArgumentException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatusConverterTest {

    @InjectMocks
    private StatusConverter converter;

    @Test
    void convertToDatabaseColumn_ReturnsValidInteger() {
        RideStatus userGender = RideStatus.ACCEPTED;

        Integer result = converter.convertToDatabaseColumn(userGender);

        assertThat(result).isEqualTo(userGender.getCode());
    }

    @Test
    void convertToEntityAttributeTest_ValidCode_ReturnsValidUserGender() {
        Integer code = RideStatus.CANCELLED.getCode();

        RideStatus result = converter.convertToEntityAttribute(code);

        assertThat(result).isEqualTo(RideStatus.CANCELLED);
    }

    @Test
    void convertToEntityAttributeTest_InvalidCode_ThrowsException() {
        Integer invalidCode = INVALID_ENUM_CODE;
        String[] args = new String[] {invalidCode.toString()};

        assertThatExceptionOfType(IllegalEnumArgumentException.class)
                .isThrownBy(() -> converter.convertToEntityAttribute(invalidCode))
                .withMessage(INVALID_ENUM_ARGUMENT)
                .satisfies(e -> Assertions.assertThat(e.getArgs()).isEqualTo(args));
    }

}
