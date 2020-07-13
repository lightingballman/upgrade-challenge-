package com.campresv.dto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation")
public class ReservationDto {

    public static final String RES_STATUS_ACTIVE = "ACTIVE";
    public static final String RES_STATUS_CANCELED = "CANCELED";

    @Id
    @GeneratedValue
    @Column(name = "reservation_id", unique = true, nullable = false)
    private Long reversionId;

    @Column(name ="reservation_day")
    private Date reversionDay;

    @Column(name ="resevered_by")
    private Long reversedBy;

    @Column(name ="start_day")
    private Date startDay;

    @Column(name ="end_day")
    private Date endDay;

    @Column(name ="reservation_status")
    private String reservationStatus;

    public ReservationDto(){}

    public ReservationDto(Date reversionDay, Long reversedBy, Date startDay, Date endDay, String reservationStatus) {
        this.reversionDay = reversionDay;
        this.reversedBy = reversedBy;
        this.startDay = startDay;
        this.endDay = endDay;
        this.reservationStatus = reservationStatus;
    }

    public ReservationDto(Date reversionDay, Long reversedBy, Date startDay, Date endDay) {
        this.reversionDay = reversionDay;
        this.reversedBy = reversedBy;
        this.startDay = startDay;
        this.endDay = endDay;
        this.reservationStatus = RES_STATUS_ACTIVE;
    }

    public Long getReversionId() {
        return reversionId;
    }

    public void setReversionId(Long reversionId) {
        this.reversionId = reversionId;
    }

    public Date getReversionDay() {
        return reversionDay;
    }

    public void setReversionDay(Date reversionDay) {
        this.reversionDay = reversionDay;
    }

    public Long getReversedBy() {
        return reversedBy;
    }

    public void setReversedBy(Long reversedBy) {
        this.reversedBy = reversedBy;
    }

    public Date getStartDay() {
        return startDay;
    }

    public void setStartDay(Date startDay) {
        this.startDay = startDay;
    }

    public Date getEndDay() {
        return endDay;
    }

    public void setEndDay(Date endDay) {
        this.endDay = endDay;
    }

    public String getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
}

