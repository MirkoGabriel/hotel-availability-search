package com.mindata.hotelsearch.infraestructure.adapter.out.id;

import com.mindata.hotelsearch.domain.port.SearchIdGeneratorPort;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UuidSearchIdGenerator implements SearchIdGeneratorPort {
    @Override
    public String generate() {
        return UUID.randomUUID().toString();
    }
}
