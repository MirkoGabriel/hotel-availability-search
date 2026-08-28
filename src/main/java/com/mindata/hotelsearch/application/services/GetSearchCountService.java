package com.mindata.hotelsearch.application.services;

import com.mindata.hotelsearch.application.port.GetSearchCountUseCase;
import com.mindata.hotelsearch.domain.port.SearchQueryPort;
import com.mindata.hotelsearch.domain.exception.SearchNotFoundException;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCountResult;

public class GetSearchCountService implements GetSearchCountUseCase {
    private final SearchQueryPort searchQueryPort;

    public GetSearchCountService(SearchQueryPort searchQueryPort) {
        this.searchQueryPort = searchQueryPort;
    }

    public SearchCountResult execute(String searchId) {
        Search search = searchQueryPort.findById(searchId)
                .orElseThrow(() -> new SearchNotFoundException(searchId));
        long count = searchQueryPort.countByCriteria(search.criteria());
        return new SearchCountResult(search, count);
    }
}
