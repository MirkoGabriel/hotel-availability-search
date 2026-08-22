package com.mindata.hotelsearch.infraestructure.adapter.in.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

@Schema(description = "Search payload")
public record SearchPayloadDto(
        String hotelId,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkIn,
        @JsonFormat(pattern = "dd/MM/yyyy")
        LocalDate checkOut,
        List<Integer> ages
) {
}
