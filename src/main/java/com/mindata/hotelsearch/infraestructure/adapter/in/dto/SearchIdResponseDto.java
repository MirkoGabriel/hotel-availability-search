package com.mindata.hotelsearch.infraestructure.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Search identifier response")
public record SearchIdResponseDto(
        @Schema(example = "xxxxx")
        String searchId
) {
}
