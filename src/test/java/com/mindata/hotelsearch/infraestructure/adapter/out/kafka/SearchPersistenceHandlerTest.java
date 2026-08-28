package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.mindata.hotelsearch.domain.port.SaveSearchPort;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SearchPersistenceHandlerTest {
    @Mock
    private SaveSearchPort saveSearchPort;

    @InjectMocks
    private SearchPersistenceHandler handler;

    @Test
    void shouldPersistSearch() {
        Search search = new Search("search-1",
                new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30)));

        handler.persist(search);

        verify(saveSearchPort).save(search);
    }

    @Test
    void shouldPropagatePersistenceErrors() {
        Search search = new Search("search-1",
                new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30)));
        doThrow(new RuntimeException("db error")).when(saveSearchPort).save(search);

        assertThrows(IllegalStateException.class, () -> handler.persist(search));
    }
}
