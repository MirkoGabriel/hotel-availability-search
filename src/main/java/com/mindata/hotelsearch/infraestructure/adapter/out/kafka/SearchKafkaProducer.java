package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.mindata.hotelsearch.application.port.out.PublishSearchEventPort;
import com.mindata.hotelsearch.domain.model.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class SearchKafkaProducer implements PublishSearchEventPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchKafkaProducer.class);

    private final KafkaTemplate<String, SearchEventMessage> kafkaTemplate;
    private final String topicName;

    public SearchKafkaProducer(KafkaTemplate<String, SearchEventMessage> kafkaTemplate,
                               @Value("${app.kafka.topic.hotel-availability-searches}") String topicName) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicName = topicName;
    }

    @Override
    public void publish(Search search) {
        SearchEventMessage message = SearchEventMessage.from(search);
        kafkaTemplate.send(topicName, search.searchId(), message);
        LOGGER.info("Published search event for searchId={}", search.searchId());
    }
}
