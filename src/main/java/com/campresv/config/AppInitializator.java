package com.campresv.config;

import com.campresv.dao.CampScheduleRepository;
import com.campresv.dto.CampScheduleDto;
import com.campresv.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Component
class AppInitializator {

    @Autowired
    private CampScheduleRepository campScheduleRepository;


    @PostConstruct
    private void init() {
        initCalendar();
    }

    private void initCalendar(){
        Calendar now = Calendar.getInstance();
        Calendar nextYear = (Calendar) now.clone();
        nextYear.add(Calendar.YEAR, 1);
        nextYear.add(Calendar.DATE , 1);
        List<CampScheduleDto> oneYearEmptySchedule = new ArrayList<>();
        Long id = 1L;
        while(now.before(nextYear)){
            CampScheduleDto campScheduleDto = new CampScheduleDto();
            campScheduleDto.setId(id);
            campScheduleDto.setCalendarDay(DateUtil.normalizeCalendar(now));
            campScheduleDto.setIsAvailable(0);
            campScheduleDto.setVersion(0l);
            oneYearEmptySchedule.add(campScheduleDto);
            id ++;
            now.add(Calendar.DATE , 1);
        }
        campScheduleRepository.saveAll(oneYearEmptySchedule);
    }
}
