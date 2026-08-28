package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.mindata.hotelsearch.domain.port.PublishSearchEventPort;
import com.mindata.hotelsearch.domain.model.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchKafkaProducerAdapter implements PublishSearchEventPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchKafkaProducerAdapter.class);

    private final KafkaTemplate<String, SearchEventMessage> kafkaTemplate;
    private final String topicName;

    public SearchKafkaProducerAdapter(KafkaTemplate<String, SearchEventMessage> kafkaTemplate,
                                      @Value("${app.kafka.topic.hotel-availability-searches}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void publish(Search search) {
        SearchEventMessage message = SearchEventMessage.from(search);
        try {
            kafkaTemplate.send(topicName, search.searchId(), message)
                    .whenComplete((result, exception) -> {
                        if (exception != null) {
                            LOGGER.error("Failed to publish search event for searchId={}", search.searchId(), exception);
                        } else {
                            LOGGER.info("Published search event for searchId={}", search.searchId());
                        }
                    });
        } catch (Exception exception) {
            LOGGER.error("Failed to publish search event for searchId={}", search.searchId(), exception);
            throw new IllegalStateException("Failed to publish search event for searchId=" + search.searchId(), exception);
        }
    }
}
