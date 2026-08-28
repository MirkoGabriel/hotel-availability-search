package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEventMessageTest {
    @Test
    void shouldMapToAndFromDomain() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30, 29));
        Search search = new Search("search-1", criteria);

        SearchEventMessage message = SearchEventMessage.from(search);
        Search mapped = message.toDomain();

        assertAll(
                () -> assertEquals("search-1", message.searchId()),
                () -> assertEquals(search.searchId(), mapped.searchId()),
                () -> assertEquals(search.criteria(), mapped.criteria())
        );
    }
}
