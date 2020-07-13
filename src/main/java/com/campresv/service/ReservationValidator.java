package com.campresv.service;

import com.campresv.model.CampsiteQueryReqModel;
import com.campresv.model.CampsiteReservationCancelReqModel;
import com.campresv.model.CampsiteReservationModificationReqModel;
import com.campresv.model.CampsiteReservationReqModel;
import com.campresv.util.DateUtil;

import java.time.LocalDate;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class ReservationValidator {

    public static final String VALIDATION_MSG ="First/Last name and email are request, date format YYYY-MM-DD !";
    public static final String DATE_MSG = "Minimium reservation is 1 day, max 3 days";
    public static final String MAX_ADVANCE_MSG = "The campsite can be reserved minimum 1 day(s) ahead of arrival and up to 1 month in advance" ;
    public static final String regexName = "\\p{Upper}(\\p{Lower}+\\s?)";
    public static final String regexEmail = "^(.+)@(.+)$";

    public static final int MIN_RESV_DAY = 1;
    public static final int MAX_RESV_DAY = 3;
    public static final int MIN_RESV_AHEAD_DAYS = 1;
    public static final int MAX_RESV_AHEAD_MONTH = 1;

    public static String validateReservation(CampsiteReservationReqModel request){
        String msg = "";
        if(!isValid(request)){
            msg = msg + VALIDATION_MSG + "\n";
        }

        if(! validateDate(request.startDay, request.endDay)){
            msg = msg + DATE_MSG + "\n";
        }

        if( ! validateStartDay(request.startDay)){
            msg = msg + MAX_ADVANCE_MSG + "\n";
        }
        return msg.equals("")? null: msg;
    }

    public static boolean isValid(CampsiteReservationReqModel request){
        return (request.firstName != null && request.firstName.trim().length() > 0 && request.firstName.matches(regexName)) &&
                (request.lastName != null && request.lastName.trim().length() > 0 && request.lastName.matches(regexName)) &&
                (request.emailAddress != null && request.emailAddress.trim().length() > 0 && request.emailAddress.matches(regexEmail))&&
                DateUtil.isValidateDate(request.startDay) &&
                DateUtil.isValidateDate(request.endDay);
    }

    public static boolean validateDate(String resvStartDay, String resvEndDay){
        Date start = DateUtil.parseDateNowOnError(resvStartDay);
        Date end = DateUtil.parseDateNowOnError(resvEndDay);
        long days = DAYS.between(start.toInstant(), end.toInstant());
        return days >= 0 && days <= 2;
    }

    public static boolean validateStartDay(String resvStartDay){
        Date startDay = DateUtil.parseDateNowOnError(resvStartDay);
        return validateStarDay(startDay);
    }

    public static boolean validateStarDay(Date startDay){
        Date now = new Date();

        LocalDate localStart = new java.sql.Date(startDay.getTime()).toLocalDate();
        LocalDate localNow = new java.sql.Date(now.getTime()).toLocalDate();

        LocalDate nextMonth = localNow.plusMonths(1).plusDays(1);
        LocalDate nextDay = localNow.plusDays(1);

        return (!localStart.isBefore(nextDay)) && (!localStart.isAfter(nextMonth));
    }

    public static String validateQueryRequest(CampsiteQueryReqModel request){
        String msg = "";
        if(((request.startDay == null || DateUtil.isValidateDate(request.startDay))
                && (request.endDay == null || DateUtil.isValidateDate(request.endDay)))){
            return null;
        }else {
            return "invilad date start/end day start = " + request.startDay + " end = " + request.endDay;
        }
    }

    public static String validateUpdateReservation(CampsiteReservationModificationReqModel request){
        String msg = "";
        if(! validateDate(request.startDay, request.endDay)){
            msg = msg + DATE_MSG + "\n";
        }

        if( ! validateStartDay(request.startDay)){
            msg = msg + MAX_ADVANCE_MSG + "\n";
        }
        return msg.equals("")? null: msg;
    }
}
