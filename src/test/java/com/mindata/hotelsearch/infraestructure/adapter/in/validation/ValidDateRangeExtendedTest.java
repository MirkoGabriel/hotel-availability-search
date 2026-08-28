package com.mindata.hotelsearch.infraestructure.adapter.in.validation;

import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidDateRangeExtendedTest {
    private final Validator validator =
            Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldRejectValidationWhenCheckInIsMissing() {
        SearchRequestDto request = new SearchRequestDto(
                "hotel",
                null,
                LocalDate.now().plusDays(3),
                List.of(30)
        );

        assertFalse(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAcceptSameMonthRange() {
        SearchRequestDto request = new SearchRequestDto(
                "hotel",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(2),
                List.of(30)
        );

        assertTrue(validator.validate(request).isEmpty());
    }

    @Test
    void shouldAcceptValidDates() {
        SearchRequestDto request = new SearchRequestDto(
                "hotel",
                LocalDate.now().plusDays(10),
                LocalDate.now().plusDays(11),
                List.of(30)
        );

        assertTrue(validator.validate(request).isEmpty());
    }
}
