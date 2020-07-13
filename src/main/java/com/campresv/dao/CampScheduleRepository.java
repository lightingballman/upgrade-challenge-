package com.campresv.dao;

import com.campresv.dto.CampScheduleDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CampScheduleRepository extends JpaRepository<CampScheduleDto, Integer> {

    @Query("from com.campresv.dto.CampScheduleDto where calendarDay >= :startDay and calendarDay <= :endDay order by calendarDay")
    List<CampScheduleDto> findByStartDayAndEndDay(@Param("startDay") Date startDay, @Param("endDay") Date endDay);

    @Query("from com.campresv.dto.CampScheduleDto where resvationId = :resvationId")
    List<CampScheduleDto> findByResvationId(@Param("resvationId") Long resvationId);

}
