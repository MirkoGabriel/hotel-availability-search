package com.mindata.hotelsearch.domain.port;

import com.mindata.hotelsearch.domain.model.Search;

public interface PublishSearchEventPort {
    void publish(Search search);
}
