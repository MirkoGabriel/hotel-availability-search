package com.mindata.hotelsearch.application.port.in;

import com.mindata.hotelsearch.domain.model.SearchCriteria;

public interface CreateSearchUseCase {
    String execute(SearchCriteria criteria);
}
