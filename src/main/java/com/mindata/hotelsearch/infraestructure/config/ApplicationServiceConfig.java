package com.mindata.hotelsearch.infraestructure.config;

import com.mindata.hotelsearch.application.port.CreateSearchUseCase;
import com.mindata.hotelsearch.application.port.GetSearchCountUseCase;
import com.mindata.hotelsearch.application.services.CreateSearchService;
import com.mindata.hotelsearch.application.services.GetSearchCountService;
import com.mindata.hotelsearch.domain.port.PublishSearchEventPort;
import com.mindata.hotelsearch.domain.port.SearchIdGeneratorPort;
import com.mindata.hotelsearch.domain.port.SearchQueryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationServiceConfig {
    @Bean
    public CreateSearchUseCase createSearchUseCase(SearchIdGeneratorPort searchIdGeneratorPort,
                                                   PublishSearchEventPort publishSearchEventPort) {
        return new CreateSearchService(searchIdGeneratorPort, publishSearchEventPort);
    }

    @Bean
    public GetSearchCountUseCase getSearchCountUseCase(SearchQueryPort searchQueryPort) {
        return new GetSearchCountService(searchQueryPort);
    }
}
