package com.mindata.hotelsearch.infraestructure.adapter.out.persistence;

import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.out.persistene.SearchEntity;
import com.mindata.hotelsearch.infraestructure.adapter.out.persistene.SearchEntityMapper;
import com.mindata.hotelsearch.infraestructure.adapter.out.persistene.SearchJpaRepository;
import com.mindata.hotelsearch.infraestructure.adapter.out.persistene.SearchPersistenceAdapter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchPersistenceAdapterTest {
    @Mock
    private SearchJpaRepository searchJpaRepository;

    @Mock
    private SearchEntityMapper searchEntityMapper;

    @InjectMocks
    private SearchPersistenceAdapter adapter;

    @Test
    void shouldSaveSearch() {
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(30));
        Search search = new Search("id-1", criteria);
        SearchEntity entity = new SearchEntity("id-1", "hotel", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), "[30]");
        when(searchEntityMapper.toEntity(search)).thenReturn(entity);

        adapter.save(search);

        verify(searchJpaRepository).save(entity);
    }

    @Test
    void shouldFindAndCountSearches() {
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(30, 29));
        Search search = new Search("id-1", criteria);
        SearchEntity entity = new SearchEntity("id-1", "hotel", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), "[30,29]");
        when(searchJpaRepository.findById("id-1")).thenReturn(Optional.of(entity));
        when(searchEntityMapper.toDomain(entity)).thenReturn(search);
        when(searchEntityMapper.serializeAges(criteria.ages())).thenReturn("[30,29]");
        when(searchJpaRepository.countIdenticalSearches("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), "[30,29]")).thenReturn(3L);

        assertAll(
                () -> assertTrue(adapter.findById("id-1").isPresent()),
                () -> assertEquals(3L, adapter.countByCriteria(criteria))
        );
    }
}
