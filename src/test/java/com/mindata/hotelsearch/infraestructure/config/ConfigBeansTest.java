package com.mindata.hotelsearch.infraestructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.hotelsearch.infraestructure.adapter.out.kafka.SearchEventMessage;
import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class AsyncConfigTest {

    private final AsyncConfig config = new AsyncConfig();

    @Test
    void shouldCreateVirtualThreadExecutor() {
        AsyncTaskExecutor executor = config.virtualThreadExecutor();
        assertNotNull(executor);
    }
}

class OpenApiConfigTest {

    private final OpenApiConfig config = new OpenApiConfig();

    @Test
    void shouldCreateOpenApiBean() {
        OpenAPI openAPI = config.hotelSearchOpenApi();
        assertAll(
                () -> assertNotNull(openAPI),
                () -> assertNotNull(openAPI.getInfo()),
                () -> assertNotNull(openAPI.getInfo().getTitle())
        );
    }
}

class KafkaConfigTest {

    @Test
    void shouldCreateKafkaBeans() {
        KafkaConfig config = new KafkaConfig();
        setField(config, "bootstrapServers", "localhost:9092");
        setField(config, "groupId", "test-group");

        ObjectMapper objectMapper = config.objectMapper();
        ProducerFactory<String, SearchEventMessage> producerFactory = config.producerFactory(objectMapper);
        KafkaTemplate<String, SearchEventMessage> kafkaTemplate = config.kafkaTemplate(producerFactory);
        var consumerFactory = config.consumerFactory(objectMapper);
        var listenerFactory = config.kafkaListenerContainerFactory(consumerFactory);

        assertAll(
                () -> assertNotNull(objectMapper),
                () -> assertNotNull(producerFactory),
                () -> assertNotNull(kafkaTemplate),
                () -> assertNotNull(consumerFactory),
                () -> assertNotNull(listenerFactory)
        );
    }

    private void setField(Object target, String fieldName, String value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException(exception);
        }
    }
}

