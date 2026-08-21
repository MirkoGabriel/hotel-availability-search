package com.mindata.hotelsearch.application;

import com.mindata.hotelsearch.application.port.out.SearchQueryPort;
import com.mindata.hotelsearch.application.services.GetSearchCountService;
import com.mindata.hotelsearch.domain.exception.SearchNotFoundException;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCountResult;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetSearchCountServiceTest {
    @Mock
    private SearchQueryPort searchQueryPort;

    @InjectMocks
    private GetSearchCountService getSearchCountService;

    @Test
    void shouldReturnSearchAndCount() {
        SearchCriteria criteria = new SearchCriteria("1234aBc", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(30, 29, 1, 3));
        Search search = new Search("search-1", criteria);
        when(searchQueryPort.findById("search-1")).thenReturn(Optional.of(search));
        when(searchQueryPort.countByCriteria(criteria)).thenReturn(2L);

        SearchCountResult result = getSearchCountService.execute("search-1");

        assertAll(
                () -> Assertions.assertEquals(search, result.search()),
                () -> Assertions.assertEquals(2L, result.count())
        );
    }

    @Test
    void shouldThrowWhenSearchNotFound() {
        when(searchQueryPort.findById("missing")).thenReturn(Optional.empty());
        assertThrows(SearchNotFoundException.class, () -> getSearchCountService.execute("missing"));
    }

}
