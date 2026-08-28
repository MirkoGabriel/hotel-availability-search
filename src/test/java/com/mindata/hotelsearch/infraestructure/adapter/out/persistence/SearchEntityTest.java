package com.mindata.hotelsearch.infraestructure.adapter.out.persistence;

import com.mindata.hotelsearch.infraestructure.adapter.out.persistene.SearchEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchEntityTest {
    @Test
    void shouldExposeEntityValues() {
        SearchEntity entity = new SearchEntity(
                "id-1",
                "hotel",
                LocalDate.of(2023, 12, 29),
                LocalDate.of(2023, 12, 31),
                "[30]"
        );

        assertAll(
                () -> assertEquals("id-1", entity.getSearchId()),
                () -> assertEquals("hotel", entity.getHotelId()),
                () -> assertEquals(LocalDate.of(2023, 12, 29), entity.getCheckIn()),
                () -> assertEquals(LocalDate.of(2023, 12, 31), entity.getCheckOut()),
                () -> assertEquals("[30]", entity.getAges())
        );
    }
}
