package com.mindata.hotelsearch.application;

import com.mindata.hotelsearch.domain.port.PublishSearchEventPort;
import com.mindata.hotelsearch.domain.port.SearchIdGeneratorPort;
import com.mindata.hotelsearch.application.services.CreateSearchService;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateSearchServiceTest {
    @Mock
    private SearchIdGeneratorPort searchIdGeneratorPort;

    @Mock
    private PublishSearchEventPort publishSearchEventPort;

    @InjectMocks
    private CreateSearchService createSearchService;

    @Test
    void shouldGenerateUniqueSearchIdAndPublishEvent() {
        SearchCriteria criteria = new SearchCriteria("1234aBc", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(30, 29));
        when(searchIdGeneratorPort.generate()).thenReturn("search-123");

        String searchId = createSearchService.execute(criteria);

        ArgumentCaptor<Search> searchCaptor = ArgumentCaptor.forClass(Search.class);
        verify(publishSearchEventPort).publish(searchCaptor.capture());

        Search publishedSearch = searchCaptor.getValue();
        assertAll(
                () -> Assertions.assertEquals("search-123", searchId),
                () -> Assertions.assertEquals("search-123", publishedSearch.searchId()),
                () -> Assertions.assertEquals(criteria, publishedSearch.criteria())
        );
    }
}
