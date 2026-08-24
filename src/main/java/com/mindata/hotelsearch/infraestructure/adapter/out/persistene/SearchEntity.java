package com.mindata.hotelsearch.infraestructure.adapter.out.persistene;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "hotel_availability_search")
public class SearchEntity {
    @Id
    @Column(name = "search_id", nullable = false, length = 36)
    private String searchId;

    @Column(name = "hotel_id", nullable = false, length = 50)
    private String hotelId;

    @Column(name = "check_in", nullable = false)
    private LocalDate checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDate checkOut;

    @Column(name = "ages", nullable = false, length = 200)
    private String ages;

    protected SearchEntity() {
    }

    public SearchEntity(String searchId, String hotelId, LocalDate checkIn, LocalDate checkOut, String ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }

    public String getSearchId() {
        return searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public String getAges() {
        return ages;
    }
}
