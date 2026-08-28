package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.mindata.hotelsearch.domain.port.SaveSearchPort;
import com.mindata.hotelsearch.domain.model.Search;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;

@Component
public class SearchPersistenceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchPersistenceHandler.class);

    private final SaveSearchPort saveSearchPort;

    public SearchPersistenceHandler(SaveSearchPort saveSearchPort) {
        this.saveSearchPort = saveSearchPort;
    }

    @Async("virtualThreadExecutor")
    public void persistAsync(Search search) {
        saveSearchPort.save(search);
        LOGGER.info("Persisted search searchId={}", search.searchId());
    }
}
