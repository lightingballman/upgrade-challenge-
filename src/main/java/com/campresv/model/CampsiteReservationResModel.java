package com.campresv.model;

public class CampsiteReservationResModel {

    public Long resversionId;
    public boolean resversationStatus;
    public String additionMsg;
    public String startDay;
    public String endDay;

    public CampsiteReservationResModel() {};

    public CampsiteReservationResModel(boolean resversationStatus, String additionMsg, String startDay, String endDay) {
        this.resversationStatus = resversationStatus;
        this.additionMsg = additionMsg;
        this.startDay = startDay;
        this.endDay = endDay;
    }

    public CampsiteReservationResModel(Long resversionId, boolean resversationStatus, String additionMsg, String startDay, String endDay) {
        this.resversionId = resversionId;
        this.resversationStatus = resversationStatus;
        this.additionMsg = additionMsg;
        this.startDay = startDay;
        this.endDay = endDay;
    }
}
