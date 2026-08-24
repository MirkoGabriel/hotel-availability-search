package com.mindata.hotelsearch.infraestructure.adapter.out.persistene;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SearchEntityMapper {
    private static final TypeReference<List<Integer>> AGES_TYPE = new TypeReference<>() {
    };

    private final ObjectMapper objectMapper;

    public SearchEntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SearchEntity toEntity(Search search) {
        SearchCriteria criteria = search.criteria();
        return new SearchEntity(
                search.searchId(),
                criteria.hotelId(),
                criteria.checkIn(),
                criteria.checkOut(),
                serializeAges(criteria.ages())
        );
    }

    public Search toDomain(SearchEntity entity) {
        SearchCriteria criteria = new SearchCriteria(
                entity.getHotelId(),
                entity.getCheckIn(),
                entity.getCheckOut(),
                deserializeAges(entity.getAges())
        );
        return new Search(entity.getSearchId(), criteria);
    }

    public String serializeAges(List<Integer> ages) {
        try {
            return objectMapper.writeValueAsString(ages);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to serialize ages", exception);
        }
    }

    public List<Integer> deserializeAges(String ages) {
        try {
            return objectMapper.readValue(ages, AGES_TYPE);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Unable to deserialize ages", exception);
        }
    }
}
