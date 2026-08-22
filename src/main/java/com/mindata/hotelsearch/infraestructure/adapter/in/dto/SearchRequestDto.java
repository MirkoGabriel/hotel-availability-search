package com.mindata.hotelsearch.infraestructure.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.util.List;

@Schema(description = "Hotel search request")
public record SearchRequestDto(
        @NotBlank(message = "hotelId must not be blank")
        @Schema(example = "1234aBc")
        String hotelId,

        @NotBlank(message = "checkIn must not be blank")
        @Schema(example = "29/12/2023")
        String checkIn,

        @NotBlank(message = "checkOut must not be blank")
        @Schema(example = "31/12/2023")
        String checkOut,

        @NotNull(message = "ages must not be null")
        @NotEmpty(message = "ages must not be empty")
        @Schema(example = "[30, 29, 1, 3]")
        List<@NotNull(message = "age must not be null") @PositiveOrZero(message = "ages must be >= 0") Integer> ages
) {
}
