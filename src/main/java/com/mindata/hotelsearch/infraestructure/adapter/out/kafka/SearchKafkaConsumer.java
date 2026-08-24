package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.mindata.hotelsearch.domain.model.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SearchKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchKafkaConsumer.class);

    private final SearchPersistenceHandler searchPersistenceHandler;

    public SearchKafkaConsumer(SearchPersistenceHandler searchPersistenceHandler) {
        this.searchPersistenceHandler = searchPersistenceHandler;
    }

    @KafkaListener(
            topics = "${app.kafka.topic.hotel-availability-searches}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(SearchEventMessage message) {
        Search search = message.toDomain();
        LOGGER.info("Received search event for searchId={}", search.searchId());
        searchPersistenceHandler.persistAsync(search);
    }
}
