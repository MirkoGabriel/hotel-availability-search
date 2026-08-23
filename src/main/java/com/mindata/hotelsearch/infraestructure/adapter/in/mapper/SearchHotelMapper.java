package com.mindata.hotelsearch.infraestructure.adapter.in.mapper;

import com.mindata.hotelsearch.domain.model.SearchCountResult;
import com.mindata.hotelsearch.domain.model.SearchCriteria;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchCountResponseDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchPayloadDto;
import com.mindata.hotelsearch.infraestructure.adapter.in.dto.SearchRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SearchHotelMapper {
    SearchCriteria toCriteria(SearchRequestDto request);

    @Mapping(target = "searchId", source = "search.searchId")
    @Mapping(target = "search", source = "search.criteria")
    @Mapping(target = "count", source = "count")
    SearchCountResponseDto toCountResponse(SearchCountResult result);

    SearchPayloadDto toPayload(SearchCriteria criteria);
}
