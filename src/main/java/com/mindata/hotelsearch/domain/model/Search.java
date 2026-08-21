package com.mindata.hotelsearch.domain.model;

public record Search(String searchId, SearchCriteria criteria) {

    public Search {
        if (searchId == null || searchId.isBlank()) {
            throw new IllegalArgumentException("searchId must not be null or blank");
        }

        if (criteria == null) {
            throw new IllegalArgumentException("criteria must not be null");
        }
    }
}
