package com.campresv.dto;

import org.springframework.data.annotation.Version;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "camp_schedule")
public class CampScheduleDto {

    @Id
    //@GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "calendar_day")
    private Date calendarDay;

    @Column(name = "reservation_id")
    private Long resvationId;

    @Column(name = "is_available")
    private Integer isAvailable = 1;

    @Version
    @Column(name = "version")
    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCalendarDay() {
        return calendarDay;
    }

    public void setCalendarDay(Date calendarDay) {
        this.calendarDay = calendarDay;
    }

    public Long getResvationId() {
        return resvationId;
    }

    public void setResvationId(Long resvationId) {
        this.resvationId = resvationId;
    }

    public Integer getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(Integer isAvailable) {
        this.isAvailable = isAvailable;
    }

    public void setNotAvailable() {
        this.isAvailable = 0;
    }

    public void setAvailable() {
        this.isAvailable = 1;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public boolean isAvailable(){
        return this.isAvailable == null || isAvailable == 1;
    }
}

