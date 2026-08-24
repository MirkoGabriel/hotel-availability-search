package com.mindata.hotelsearch.infraestructure.adapter.out.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.out.persistene.SearchEntity;
import com.mindata.hotelsearch.infraestructure.adapter.out.persistene.SearchEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchEntityMapperTest {
    private SearchEntityMapper mapper;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        mapper = new SearchEntityMapper(objectMapper);
    }

    @Test
    void shouldMapBetweenDomainAndEntity() {
        SearchCriteria criteria = new SearchCriteria("1234aBc", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(30, 29, 1, 3));
        Search search = new Search("search-1", criteria);

        SearchEntity entity = mapper.toEntity(search);
        Search mappedBack = mapper.toDomain(entity);

        assertAll(
                () -> assertEquals("search-1", entity.getSearchId()),
                () -> assertEquals(List.of(30, 29, 1, 3), mapper.deserializeAges(entity.getAges())),
                () -> assertEquals(search, mappedBack)
        );
    }
}
