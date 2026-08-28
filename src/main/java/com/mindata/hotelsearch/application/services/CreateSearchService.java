package com.mindata.hotelsearch.application.services;

import com.mindata.hotelsearch.application.port.CreateSearchUseCase;
import com.mindata.hotelsearch.domain.port.PublishSearchEventPort;
import com.mindata.hotelsearch.domain.port.SearchIdGeneratorPort;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.springframework.stereotype.Service;

public class CreateSearchService implements CreateSearchUseCase {
    private final SearchIdGeneratorPort searchIdGeneratorPort;
    private final PublishSearchEventPort publishSearchEventPort;

    public CreateSearchService(SearchIdGeneratorPort searchIdGeneratorPort,
                               PublishSearchEventPort publishSearchEventPort) {
        this.searchIdGeneratorPort = searchIdGeneratorPort;
        this.publishSearchEventPort = publishSearchEventPort;
    }

    public String execute(SearchCriteria criteria) {
        String searchId = searchIdGeneratorPort.generate();
        Search search = new Search(searchId, criteria);
        publishSearchEventPort.publish(search);
        return searchId;
    }
}
