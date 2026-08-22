package com.mindata.hotelsearch.infraestructure.adapter.in.mapper;

import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCountResult;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchCountResponseDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SearchWebMapperTest {
    private SearchWebMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new SearchWebMapper();
    }

    @Test
    void shouldMapRequestToCriteria() {
        SearchRequestDto request = new SearchRequestDto("1234aBc", "29/12/2023", "31/12/2023", List.of(30, 29, 1, 3));
        SearchCriteria criteria = mapper.toCriteria(request);

        assertAll(
                () -> assertEquals("1234aBc", criteria.hotelId()),
                () -> assertEquals(LocalDate.of(2023, 12, 29), criteria.checkIn()),
                () -> assertEquals(LocalDate.of(2023, 12, 31), criteria.checkOut()),
                () -> assertEquals(List.of(30, 29, 1, 3), criteria.ages())
        );
    }

    @Test
    void shouldMapCountResultToResponse() {
        SearchCriteria criteria = new SearchCriteria("1234aBc", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30, 29, 1, 3));
        Search search = new Search("search-1", criteria);
        SearchCountResult result = new SearchCountResult(search, 5L);

        SearchCountResponseDto response = mapper.toCountResponse(result);

        assertAll(
                () -> assertEquals("search-1", response.searchId()),
                () -> assertEquals("1234aBc", response.search().hotelId()),
                () -> assertEquals("29/12/2023", response.search().checkIn()),
                () -> assertEquals("31/12/2023", response.search().checkOut()),
                () -> assertEquals(List.of(30, 29, 1, 3), response.search().ages()),
                () -> assertEquals(5L, response.count())
        );
    }

    @Test
    void shouldRejectInvalidDateFormat() {
        SearchRequestDto request = new SearchRequestDto("1234aBc", "2023-12-29", "31/12/2023", List.of(30));
        assertThrows(IllegalArgumentException.class, () -> mapper.toCriteria(request));
    }
}
