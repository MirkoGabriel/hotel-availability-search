package com.mindata.hotelsearch.application.services;

import com.mindata.hotelsearch.application.port.in.CreateSearchUseCase;
import com.mindata.hotelsearch.application.port.out.PublishSearchEventPort;
import com.mindata.hotelsearch.application.port.out.SearchIdGeneratorPort;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;

public class CreateSearchService implements CreateSearchUseCase {
    private final SearchIdGeneratorPort searchIdGeneratorPort;
    private final PublishSearchEventPort publishSearchEventPort;

    public CreateSearchService(SearchIdGeneratorPort searchIdGeneratorPort,
                               PublishSearchEventPort publishSearchEventPort) {
        this.searchIdGeneratorPort = searchIdGeneratorPort;
        this.publishSearchEventPort = publishSearchEventPort;
    }

    @Override
    public String execute(SearchCriteria criteria) {
        String searchId = searchIdGeneratorPort.generate();
        Search search = new Search(searchId, criteria);
        publishSearchEventPort.publish(search);
        return searchId;
    }
}
