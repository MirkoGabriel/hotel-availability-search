package com.mindata.hotelsearch.infraestructure.adapter.in.validation;

import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidDateRangeExtendedTest {
    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldRejectValidationWhenCheckInIsMissing() {
        SearchRequestDto request = new SearchRequestDto(
                "hotel",
                null,
                LocalDate.of(2023, 12, 31),
                List.of(30)
        );

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAcceptSameMonthRange() {
        SearchRequestDto request = new SearchRequestDto(
                "hotel",
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 2),
                List.of(30)
        );

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAcceptValidDates() {
        SearchRequestDto request = new SearchRequestDto(
                "hotel",
                LocalDate.of(2023, 2, 28),
                LocalDate.of(2023, 3, 1),
                List.of(30)
        );

        assertTrue(validator.validate(request).isEmpty());
    }
}
