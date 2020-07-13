package com.campresv.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static final DateFormat APP_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final long HALF_DAY_IN_MIL_SECOND = 12 * 60 *60 * 1000;

    public static Date normalizeDay(Date input){
        LocalDate ld = new java.sql.Date(input.getTime()).toLocalDate();
        return java.sql.Date.valueOf(ld);
        //return java.sql.Date.valueOf(ld.atStartOfDay(ZoneOffset.UTC).toLocalDate());
    }

    public static Date normalizeCalendar(Calendar input){
        LocalDate ld = new java.sql.Date(input.getTimeInMillis()).toLocalDate();
        return java.sql.Date.valueOf(ld);
    }

    public static Date getValidStartDay(Date input){
        Date now = new Date();
        if(input == null){
            input = now;
        }

        Date cutOff = getCutOffTime(now);

        if(input.before(cutOff)){
            return normalizeDay(input);
        }else {
            LocalDate ld = new java.sql.Date(input.getTime()).toLocalDate();
            ld.plusDays(1L);
            return java.sql.Date.valueOf(ld);
        }
    }

    //assum start is already valide
    public static Date getValidEndDay(Date start, Date end){
        if(end == null || end.before(start)){
            LocalDate ld = new java.sql.Date(start.getTime()).toLocalDate();
            ld.plusMonths(1);
            ld.plusDays(3L);
            return java.sql.Date.valueOf(ld);
        }else {
            return normalizeDay(end);
        }
    }

    public static Date getCutOffTime(Date input){
        Date beginOfDay = normalizeDay(input);
        Date cutOff =  new Date();
        cutOff.setTime(beginOfDay.getTime() + HALF_DAY_IN_MIL_SECOND);
        return cutOff;
    }

    public static boolean isValidateDate(String input){
        try{
            APP_DATE_FORMAT.parse(input);
            return true;
        }catch(ParseException pe){
            return false;
        }
    }

    public static Date parseDateNowOnError(String input){
        try{
            Date res = APP_DATE_FORMAT.parse(input);
            return res;
        }catch(ParseException pe){
            return new Date();
        }
    }
}
