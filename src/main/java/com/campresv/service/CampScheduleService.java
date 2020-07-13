package com.campresv.service;

import com.campresv.dao.CampScheduleRepository;
import com.campresv.dto.CampScheduleDto;
import com.campresv.dto.ReservationDto;
import com.campresv.exceptions.ReservationException;
import com.campresv.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.OptimisticLockException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class CampScheduleService {

    @Autowired
    private CampScheduleRepository campScheduleRepository;

    public List<CampScheduleDto> getCampSchedule(Date start, Date end) {
       start = DateUtil.getValidStartDay(start);
       end =DateUtil.getValidEndDay(start, end);
       return campScheduleRepository.findByStartDayAndEndDay(start,end);
    }

    public void reserve(ReservationDto reservation, Date start, Date end) throws ReservationException {
        List<CampScheduleDto> days = campScheduleRepository.findByStartDayAndEndDay(start, end);
        for(CampScheduleDto csd : days){
            if(! csd.isAvailable()){
                throw new ReservationException(csd.getCalendarDay().toString() + "is not longer available");
            }
            csd.setNotAvailable();
            csd.setResvationId( reservation.getReversionId());
        }
        try {
            campScheduleRepository.saveAll(days);
        }catch (OptimisticLockException oe){
            throw new ReservationException(" Camp is not longer available");
        }
    }

    public void markAvailable(Long reservationId){
        List<CampScheduleDto> days = campScheduleRepository.findByResvationId(reservationId);
        if(days == null || days.size() == 0){
            return;
        }
        for(CampScheduleDto csd : days){
            csd.setAvailable();
            csd.setResvationId(null);
        }
        campScheduleRepository.saveAll(days);
    }
}
