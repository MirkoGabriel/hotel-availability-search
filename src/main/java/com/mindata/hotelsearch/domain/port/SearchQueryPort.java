package com.mindata.hotelsearch.domain.port;

import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;

import java.util.Optional;

public interface SearchQueryPort {
    Optional<Search> findById(String searchId);

    long countByCriteria(SearchCriteria criteria);
}
