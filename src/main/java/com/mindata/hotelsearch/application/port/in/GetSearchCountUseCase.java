package com.mindata.hotelsearch.application.port.in;

import com.mindata.hotelsearch.domain.model.SearchCountResult;

public interface GetSearchCountUseCase {
    SearchCountResult execute(String searchId);
}
