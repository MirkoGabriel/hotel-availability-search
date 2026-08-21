package com.mindata.hotelsearch.domain.model;

import java.time.LocalDate;
import java.util.List;

public record SearchCriteria(String hotelId, LocalDate checkIn, LocalDate checkOut, List<Integer> ages) {

    public SearchCriteria {
        if (hotelId == null || hotelId.isBlank()) {
            throw new IllegalArgumentException("hotelId must not be null or blank");
        }

        if (checkIn == null) {
            throw new IllegalArgumentException("checkIn must not be null");
        }

        if (checkOut == null) {
            throw new IllegalArgumentException("checkOut must not be null");
        }

        if (checkOut.isBefore(checkIn)) {
            throw new IllegalArgumentException("checkOut must be after checkIn");
        }

        if (ages == null || ages.isEmpty()) {
            throw new IllegalArgumentException("ages must not be null or empty");
        }

        ages = List.copyOf(ages);

        for (Integer age : ages) {
            if (age == null || age < 0) {
                throw new IllegalArgumentException("ages must contain values >= 0");
            }
        }
    }
}
