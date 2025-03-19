package com.example.ratesservice.unit.enums.converter;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ENUM_ARGUMENT;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.enums.converter.RecipientTypeConverter;
import com.example.ratesservice.exception.custom.IllegalEnumArgumentException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class StatusConverterTest {

    @InjectMocks
    private RecipientTypeConverter converter;

    @Test
    void convertToDatabaseColumn_ReturnsValidInteger() {
        RecipientType recipientType = RecipientType.PASSENGER;

        Integer result = converter.convertToDatabaseColumn(recipientType);

        assertThat(result).isEqualTo(recipientType.getCode());
    }

    @Test
    void convertToEntityAttributeTest_ValidCode_ReturnsValidUserGender() {
        Integer code = RecipientType.PASSENGER.getCode();

        RecipientType result = converter.convertToEntityAttribute(code);

        assertThat(result).isEqualTo(RecipientType.PASSENGER);
    }

    @Test
    void convertToEntityAttributeTest_InvalidCode_ThrowsException() {
        Integer invalidCode = 999;
        String[] args = new String[] {invalidCode.toString()};

        assertThatExceptionOfType(IllegalEnumArgumentException.class)
                .isThrownBy(() -> converter.convertToEntityAttribute(invalidCode))
                .withMessage(INVALID_ENUM_ARGUMENT)
                .satisfies(e -> Assertions.assertThat(e.getArgs()).isEqualTo(args));
    }

}
