package com.mindata.hotelsearch.infraestructure.adapter.out.memory;

import com.mindata.hotelsearch.application.port.out.PublishSearchEventPort;
import com.mindata.hotelsearch.domain.model.Search;
import org.springframework.stereotype.Component;

@Component
public class InMemoryPublishPort implements PublishSearchEventPort {
    private final InMemorySearchRepo repo;
    public InMemoryPublishPort(InMemorySearchRepo repo) {
        this.repo = repo;
    }
    @Override
    public void publish(Search search) {
        // guardar en memoria solo para probar simula que publica al topico
        repo.save(search);
    }
}
