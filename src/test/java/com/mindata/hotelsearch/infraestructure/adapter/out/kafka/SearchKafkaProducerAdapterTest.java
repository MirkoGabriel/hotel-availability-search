package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class SearchKafkaProducerAdapterTest {
    @Mock
    private KafkaTemplate<String, SearchEventMessage> kafkaTemplate;

    private SearchKafkaProducerAdapter producer;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        producer = new SearchKafkaProducerAdapter(kafkaTemplate, "hotel_availability_searches");
    }

    @Test
    void shouldPublishSearchEvent() {
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30));
        Search search = new Search("search-1", criteria);

        producer.publish(search);

        ArgumentCaptor<SearchEventMessage> captor = ArgumentCaptor.forClass(SearchEventMessage.class);
        verify(kafkaTemplate).send(org.mockito.ArgumentMatchers.eq("hotel_availability_searches"), org.mockito.ArgumentMatchers.eq("search-1"), captor.capture());
        assertEquals("search-1", captor.getValue().searchId());
    }
}
