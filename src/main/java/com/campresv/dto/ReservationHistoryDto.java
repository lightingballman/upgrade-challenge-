package com.campresv.dto;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "reservation_history")
public class ReservationHistoryDto {

    public static final String ACTION_NEW = "NEW";
    public static final String ACTION_CANCEL = "CANCEL";
    public static final String ACTION_UPDATE = "UPDATE";

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name="user_id")
    private Long userId;

    @Column(name="reservation_id")
    private Long reservation_id;

    @Column(name="action")
    private String action;

    @Column(name="action_day")
    private Date actionDay;

    @Column(name="description")
    private String description;

    public ReservationHistoryDto() {}

    public ReservationHistoryDto(Long userId, Long reservation_id, String action, Date actionDay, String description) {
        this.userId = userId;
        this.reservation_id = reservation_id;
        this.action = action;
        this.actionDay = actionDay;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Long reservation_id) {
        this.reservation_id = reservation_id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getActionDay() {
        return actionDay;
    }

    public void setActionDay(Date actionDay) {
        this.actionDay = actionDay;
    }
}



