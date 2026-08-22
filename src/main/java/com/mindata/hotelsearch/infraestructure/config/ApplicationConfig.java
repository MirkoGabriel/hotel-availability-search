package com.mindata.hotelsearch.infraestructure.config;

import com.mindata.hotelsearch.application.port.in.CreateSearchUseCase;
import com.mindata.hotelsearch.application.port.in.GetSearchCountUseCase;
import com.mindata.hotelsearch.application.port.out.PublishSearchEventPort;
import com.mindata.hotelsearch.application.port.out.SearchIdGeneratorPort;
import com.mindata.hotelsearch.application.port.out.SearchQueryPort;
import com.mindata.hotelsearch.application.services.CreateSearchService;
import com.mindata.hotelsearch.application.services.GetSearchCountService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
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
