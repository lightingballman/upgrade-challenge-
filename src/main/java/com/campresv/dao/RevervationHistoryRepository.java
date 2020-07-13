package com.campresv.dao;

import com.campresv.dto.ReservationHistoryDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevervationHistoryRepository extends JpaRepository<ReservationHistoryDto, Integer> {

}
