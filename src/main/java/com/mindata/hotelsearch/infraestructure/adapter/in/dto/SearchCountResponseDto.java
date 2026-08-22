package com.mindata.hotelsearch.infraestructure.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Search count response")
public record SearchCountResponseDto(
        String searchId,
        SearchPayloadDto search,
        long count
) {
}
