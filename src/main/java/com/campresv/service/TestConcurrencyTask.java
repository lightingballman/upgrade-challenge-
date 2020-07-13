package com.campresv.service;

import com.campresv.dao.CampScheduleRepository;
import com.campresv.dto.CampScheduleDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Random;

@Component
@Scope("prototype")
public class TestConcurrencyTask implements Runnable {
    static Random rn = new Random();

    @Autowired
    private CampScheduleRepository campScheduleRepository;

    private CampScheduleDto testDate = null;

    @Override
    @Transactional
    public void run() {
        testDate.setIsAvailable(rn.nextInt());
        campScheduleRepository.save(testDate);
        System.out.println("hashcode: " + this.hashCode());
    }

    public TestConcurrencyTask(){
    }

    public void setData(CampScheduleDto testDate){
        this.testDate = testDate;
    }
}
