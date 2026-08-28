package com.mindata.hotelsearch.domain.port;

import com.mindata.hotelsearch.domain.model.Search;

public interface SaveSearchPort {
    void save(Search search);
}
