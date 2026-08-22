package com.mindata.hotelsearch.infraestructure.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Search payload")
public record SearchPayloadDto(
        String hotelId,
        String checkIn,
        String checkOut,
        List<Integer> ages
) {
}
