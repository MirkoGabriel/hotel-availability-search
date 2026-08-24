package com.mindata.hotelsearch.infraestructure.adapter.out.persistene;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface SearchJpaRepository extends JpaRepository<SearchEntity, String> {
    @Query("SELECT COUNT(s) FROM SearchEntity s WHERE s.hotelId = :hotelId AND s.checkIn = :checkIn AND s.checkOut = :checkOut AND s.ages = :ages")
    long countIdenticalSearches(@Param("hotelId") String hotelId,
                                @Param("checkIn") LocalDate checkIn,
                                @Param("checkOut") LocalDate checkOut,
                                @Param("ages") String ages);
}
