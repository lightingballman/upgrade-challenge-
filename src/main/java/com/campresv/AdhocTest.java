package com.campresv;

import java.time.LocalDate;
import java.util.Date;

import static java.time.temporal.ChronoUnit.DAYS;

public class AdhocTest {
    public static void main(String[] args){
//        LocalDate ld = new java.sql.Date(new Date().getTime()).toLocalDate();
//        System.out.println(java.sql.Date.valueOf(ld.toString()));
//        System.out.println(new java.util.Date());

        String regexName = "\\p{Upper}(\\p{Lower}+\\s?)";
        String name = "Joh-n";
        System.out.println(name.matches(regexName));

        System.out.println(DAYS.between(new Date().toInstant(), new Date().toInstant()));


    }
}
