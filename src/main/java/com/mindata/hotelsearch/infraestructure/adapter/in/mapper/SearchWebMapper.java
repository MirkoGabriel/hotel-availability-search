package com.mindata.hotelsearch.infraestructure.adapter.in.mapper;

import com.mindata.hotelsearch.domain.model.Search;
import com.mindata.hotelsearch.domain.model.SearchCountResult;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchCountResponseDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchPayloadDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;

@Component
public class SearchWebMapper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/uuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    public SearchCriteria toCriteria(SearchRequestDto request) {
        LocalDate checkIn = parseDate(request.checkIn(), "checkIn");
        LocalDate checkOut = parseDate(request.checkOut(), "checkOut");
        return new SearchCriteria(request.hotelId(), checkIn, checkOut, List.copyOf(request.ages()));
    }

    public SearchCountResponseDto toCountResponse(SearchCountResult result) {
        Search search = result.search();
        SearchCriteria criteria = search.criteria();
        SearchPayloadDto payload = new SearchPayloadDto(
                criteria.hotelId(),
                formatDate(criteria.checkIn()),
                formatDate(criteria.checkOut()),
                criteria.ages()
        );
        return new SearchCountResponseDto(search.searchId(), payload, result.count());
    }

    private LocalDate parseDate(String value, String fieldName) {
        try {
            return LocalDate.parse(value, DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("Invalid date format for " + fieldName + ", expected dd/MM/yyyy");
        }
    }

    private String formatDate(LocalDate date) {
        return DATE_FORMATTER.format(date);
    }
}
