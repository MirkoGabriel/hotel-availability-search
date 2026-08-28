package com.mindata.hotelsearch.infraestructure.adapter.in.validation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ValidDateRangeTest {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void shouldValidateDateRange() {
        SearchRequestDto valid = new SearchRequestDto(
                "hotel",
                LocalDate.now().plusDays(1),
                LocalDate.now().plusDays(3),
                List.of(30)
        );

        SearchRequestDto invalid = new SearchRequestDto(
                "hotel",
                LocalDate.now().plusDays(5),
                LocalDate.now().plusDays(2),
                List.of(30)
        );

        assertAll(
                () -> assertTrue(validator.validate(valid).isEmpty()),
                () -> assertFalse(validator.validate(invalid).isEmpty())
        );
    }

    @Test
    void shouldRejectInvalidDateFormat() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        String json = """
        {
            "hotelId": "hotel",
            "checkIn": "2023-12-29",
            "checkOut": "31/12/2023",
            "ages": [30]
        }
        """;

        assertThrows(
                JsonProcessingException.class,
                () -> objectMapper.readValue(json, SearchRequestDto.class)
        );
    }

    @Test
    void shouldRejectPastCheckInDate() {
        SearchRequestDto request = new SearchRequestDto(
                "hotel",
                LocalDate.now().minusDays(1),
                LocalDate.now().plusDays(7),
                List.of(30)
        );

        assertFalse(validator.validate(request).isEmpty());
    }
}
