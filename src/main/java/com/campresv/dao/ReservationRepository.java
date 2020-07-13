package com.campresv.dao;

import com.campresv.dto.ReservationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationDto, Long> {

}