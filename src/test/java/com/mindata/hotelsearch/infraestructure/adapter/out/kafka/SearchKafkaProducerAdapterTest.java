package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class SearchKafkaProducerAdapterTest {
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
        ProducerRecord<String, SearchEventMessage> producerRecord =
                new ProducerRecord<>("hotel_availability_searches", "search-1", SearchEventMessage.from(search));
        RecordMetadata metadata = new RecordMetadata(new TopicPartition("hotel_availability_searches", 0), 0, 0, 0, 0, 0);
        when(kafkaTemplate.send(eq("hotel_availability_searches"), eq("search-1"), org.mockito.ArgumentMatchers.any()))
                .thenReturn(CompletableFuture.completedFuture(new SendResult<>(producerRecord, metadata)));

        producer.publish(search);

        ArgumentCaptor<SearchEventMessage> captor = ArgumentCaptor.forClass(SearchEventMessage.class);
        verify(kafkaTemplate).send(eq("hotel_availability_searches"), eq("search-1"), captor.capture());
        assertEquals("search-1", captor.getValue().searchId());
    }
}
