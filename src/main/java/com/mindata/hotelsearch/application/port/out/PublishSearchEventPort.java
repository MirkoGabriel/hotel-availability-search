package com.mindata.hotelsearch.application.port.out;

import com.mindata.hotelsearch.domain.model.Search;

public interface PublishSearchEventPort {
    void publish(Search search);
}
