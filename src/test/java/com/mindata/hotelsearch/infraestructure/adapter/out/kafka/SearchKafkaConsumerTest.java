package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.mindata.hotelsearch.domain.model.Search;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SearchKafkaConsumerTest {
    @Mock
    private SearchPersistenceHandler searchPersistenceHandler;

    @InjectMocks
    private SearchKafkaConsumer consumer;

    @Test
    void shouldDelegatePersistenceToHandler() {
        SearchEventMessage message = new SearchEventMessage(
                "search-1",
                "hotel",
                "2023-12-29",
                "2023-12-31",
                List.of(30),
                Instant.parse("2023-12-01T10:00:00Z")
        );

        consumer.consume(message);

        verify(searchPersistenceHandler).persistAsync(org.mockito.ArgumentMatchers.any(Search.class));
    }
}
