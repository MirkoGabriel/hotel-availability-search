package com.mindata.hotelsearch.domain;

import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertThrows;

class SearchTest {
    @Test
    void shouldCreateValidSearch() {
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12, 29),
                LocalDate.of(2023, 12, 31), List.of(30));
        Search search = new Search("search-1", criteria);
        Assertions.assertEquals("search-1", search.searchId());
    }

    @Test
    void shouldRejectBlankSearchId() {
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12, 29),
                LocalDate.of(2023, 12, 31), List.of(30));
        assertThrows(IllegalArgumentException.class,
                () -> new Search(" ", criteria));
    }

    @Test
    void shouldRejectNullSearchId() {
        SearchCriteria criteria = new SearchCriteria(
                "hotel",
                LocalDate.of(2023, 12, 29),
                LocalDate.of(2023, 12, 31),
                List.of(30)
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> new Search(null, criteria)
        );
    }

    @Test
    void shouldRejectNullCriteria() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Search("search-1", null)
        );
    }
}
