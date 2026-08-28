package com.mindata.hotelsearch.domain;

import com.mindata.hotelsearch.domain.model.SearchCriteria;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class SearchCriteriaTest {
    @Test
    void shouldCreateValidCriteria() {
        SearchCriteria criteria = new SearchCriteria("1234aBc", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(30, 29, 1, 3));

        assertAll(
                () -> Assertions.assertEquals("1234aBc", criteria.hotelId()),
                () -> Assertions.assertEquals(LocalDate.of(2023, 12, 29), criteria.checkIn()),
                () -> Assertions.assertEquals(LocalDate.of(2023, 12, 31), criteria.checkOut()),
                () -> Assertions.assertEquals(List.of(30, 29, 1, 3), criteria.ages())
        );
    }

    @Test
    void shouldPreserveAgeOrderForEquality() {
        SearchCriteria first = new SearchCriteria("1234aBc", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(30, 29, 1, 3));
        SearchCriteria second = new SearchCriteria("1234aBc", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(3, 29, 30, 1));

        Assertions.assertNotEquals(first, second);
    }

    @Test
    void shouldDefensivelyCopyAgesList() {
        List<Integer> ages = new ArrayList<>(List.of(30, 29));
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), ages);
        ages.add(99);

        Assertions.assertEquals(List.of(30, 29), criteria.ages());
    }

    @Test
    void shouldRejectBlankHotelId() {
        assertThrows(IllegalArgumentException.class,
                () -> new SearchCriteria("", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30)));
    }

    @Test
    void shouldRejectCheckOutBeforeCheckIn() {
        assertThrows(IllegalArgumentException.class,
                () -> new SearchCriteria("hotel", LocalDate.of(2023, 12, 31), LocalDate.of(2023, 12, 29), List.of(30)));
    }

    @Test
    void shouldRejectNegativeAge() {
        assertThrows(IllegalArgumentException.class,
                () -> new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(-1)));
    }

    @Test
    void shouldRejectEmptyAges() {
        assertThrows(IllegalArgumentException.class,
                () -> new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of()));
    }

    @Test
    void shouldAllowZeroAge() {
        SearchCriteria criteria = new SearchCriteria("hotel", LocalDate.of(2023, 12,
                29), LocalDate.of(2023, 12, 31), List.of(0));
        Assertions.assertTrue(criteria.ages().contains(0));
    }

    @Test
    void shouldRejectNullHotelId() {
        assertThrows(IllegalArgumentException.class, () -> createCriteriaWithNullHotelId());
    }

    @Test
    void shouldRejectNullCheckIn() {
        assertThrows(IllegalArgumentException.class, () -> createCriteriaWithNullCheckIn());
    }

    @Test
    void shouldRejectNullCheckOut() {
        assertThrows(IllegalArgumentException.class, () -> createCriteriaWithNullCheckOut());
    }

    @Test
    void shouldRejectNullAges() {
        assertThrows(IllegalArgumentException.class, () -> createCriteriaWithNullAges());
    }

    private void createCriteriaWithNullHotelId() {
        new SearchCriteria(null, LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), List.of(30));
    }

    private void createCriteriaWithNullCheckIn() {
        new SearchCriteria("hotel", null, LocalDate.of(2023, 12, 31), List.of(30));
    }

    private void createCriteriaWithNullCheckOut() {
        new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), null, List.of(30));
    }

    private void createCriteriaWithNullAges() {
        new SearchCriteria("hotel", LocalDate.of(2023, 12, 29), LocalDate.of(2023, 12, 31), null);
    }
}
