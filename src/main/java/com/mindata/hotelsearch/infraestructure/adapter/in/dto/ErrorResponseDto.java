package com.mindata.hotelsearch.infraestructure.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Error response")
public record ErrorResponseDto(
        String message
) {
}
