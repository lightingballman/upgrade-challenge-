package com.campresv.controller;

import com.campresv.dao.CampsiteRepository;
import com.campresv.dto.CampScheduleDto;
import com.campresv.dto.CampsiteDto;
import com.campresv.dto.ReservationDto;
import com.campresv.exceptions.ReservationException;
import com.campresv.model.*;
import com.campresv.service.CampScheduleService;
import com.campresv.service.CampsiteReservationService;
import com.campresv.service.ReservationValidator;
import com.campresv.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CampSiteController {

    @Autowired
    private CampScheduleService campScheduleService;

    @Autowired
    private CampsiteReservationService campsiteReservationService;

    @RequestMapping("/campsite/query")
    public ResponseEntity<?> querySchedule(@RequestBody CampsiteQueryReqModel reqModel){
        String err = ReservationValidator.validateQueryRequest(reqModel);
        if(err == null){
            List<CampScheduleDto> response = campScheduleService.getCampSchedule(DateUtil.parseDateNowOnError(reqModel.startDay),
                                    DateUtil.parseDateNowOnError(reqModel.endDay));
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(err);
        }
    }

    @RequestMapping("/campsite/reserve")
    public ResponseEntity<?> reserve(@RequestBody CampsiteReservationReqModel reqModel){
        String err = ReservationValidator.validateReservation(reqModel);
        if(err == null){ //no error
            CampsiteReservationResModel response = campsiteReservationService.reserve(reqModel);
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(err);
        }
    }

    @RequestMapping("/campsite/reserve/cancel")
    public ResponseEntity<?> cancelReservation(@RequestBody CampsiteReservationCancelReqModel reqModel){
        try{
            ReservationDto reservation = campsiteReservationService.cancelReservation(reqModel);
            return ResponseEntity.ok(reservation);
        }catch (ReservationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping("/campsite/reserve/update")
    public ResponseEntity<?> updateReservation(@RequestBody CampsiteReservationModificationReqModel reqModel){
        String err = ReservationValidator.validateUpdateReservation(reqModel);
        if(err != null){ //no error
            return ResponseEntity.badRequest().body(err);
        }

        try{
            ReservationDto reservation = campsiteReservationService.updateReservation(reqModel);
            return ResponseEntity.ok(reservation);
        }catch (ReservationException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}



