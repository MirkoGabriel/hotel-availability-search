package com.mindata.hotelsearch.infraestructure.adapter.out.kafka;

import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;

import java.time.Instant;
import java.util.List;

public record SearchEventMessage (
        String searchId,
        String hotelId,
        String checkIn,
        String checkOut,
        List<Integer> ages,
        Instant createdAt
){
    public static SearchEventMessage from(Search search) {
        SearchCriteria criteria = search.criteria();
        return new SearchEventMessage(
                search.searchId(),
                criteria.hotelId(),
                criteria.checkIn().toString(),
                criteria.checkOut().toString(),
                List.copyOf(criteria.ages()),
                Instant.now()
        );
    }

    public Search toDomain() {
        SearchCriteria criteria = new SearchCriteria(
                hotelId,
                java.time.LocalDate.parse(checkIn),
                java.time.LocalDate.parse(checkOut),
                ages
        );
        return new Search(searchId, criteria);
    }
}
