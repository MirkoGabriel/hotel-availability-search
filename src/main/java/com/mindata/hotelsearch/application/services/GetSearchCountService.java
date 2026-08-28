package com.mindata.hotelsearch.application.services;

import com.mindata.hotelsearch.domain.port.SearchQueryPort;
import com.mindata.hotelsearch.domain.exception.SearchNotFoundException;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCountResult;
import org.springframework.stereotype.Service;

@Service
public class GetSearchCountService {
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
