package com.campresv.service;

import com.campresv.dao.CampScheduleRepository;
import com.campresv.dao.ReservationRepository;
import com.campresv.dao.RevervationHistoryRepository;
import com.campresv.dto.CampScheduleDto;
import com.campresv.dto.ReservationDto;
import com.campresv.dto.ReservationHistoryDto;
import com.campresv.dto.UserDto;
import com.campresv.exceptions.ReservationException;
import com.campresv.model.CampsiteReservationCancelReqModel;
import com.campresv.model.CampsiteReservationModificationReqModel;
import com.campresv.model.CampsiteReservationReqModel;
import com.campresv.model.CampsiteReservationResModel;
import com.campresv.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class CampsiteReservationService {

    @Autowired
    private UserService userService;

    @Autowired
    private CampScheduleService campScheduleService;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private RevervationHistoryRepository revervationHistoryRepository;

    public CampsiteReservationResModel reserve(CampsiteReservationReqModel request){
        UserDto user = userService.createIfNoteExist(request.emailAddress, request.firstName, request.lastName);
        boolean reservationStatus = false;
        CampsiteReservationResModel responseModel = new CampsiteReservationResModel();
        ReservationDto reservation = null;
        try{
            reservation = saveReservation(user, request);
        }catch (ReservationException e){
            return new CampsiteReservationResModel(false, e.getMessage(), request.startDay, request.endDay);
        }

        ReservationHistoryDto reservationLog = new ReservationHistoryDto(user.getUserId(),
                reservation.getReversionId(), ReservationHistoryDto.ACTION_NEW, new Date(), null);

        revervationHistoryRepository.save(reservationLog);
        return new CampsiteReservationResModel(reservation.getReversionId(), true, null, request.startDay, request.endDay);
    }

    @Transactional
    public ReservationDto saveReservation(UserDto user, CampsiteReservationReqModel request) throws ReservationException{
        Date startDay = DateUtil.parseDateNowOnError(request.startDay);
        Date endDay = DateUtil.parseDateNowOnError(request.endDay);
        //1. create reservation
        ReservationDto reservation = new ReservationDto(new Date(), user.getUserId(), startDay, endDay);
        //2. get schedule obj if any not available throws Exception;
        reservation = reservationRepository.save(reservation);
        campScheduleService.reserve(reservation, startDay, endDay);
        return reservation;
    }

    public ReservationDto cancelReservation(CampsiteReservationCancelReqModel request) throws ReservationException{
        ReservationDto existing = reservationRepository.findById(request.reservationId).orElse(null);
        if(existing == null){
            throw new ReservationException("Cannot cancel, invalid reversaiont id: " + request.reservationId);
        }

        Date now = DateUtil.normalizeDay( new Date() );
        if(now.after(existing.getStartDay())){
            throw new ReservationException("Too late to cancel");
        }

        UserDto user = userService.findByEmail(request.email);
        if(user.getUserId() != existing.getReversedBy()){
            throw new ReservationException("Cannot cancel, email address does not match reservation id, reservation id: "
                    + request.reservationId + " email address: " + request.email);
        }
        return cancel(request, existing);
    }

    @Transactional
    public ReservationDto cancel(CampsiteReservationCancelReqModel request, ReservationDto existing ){
        //1. mark available
        campScheduleService.markAvailable(request.reservationId);

        //2. Make reservation canceled
        existing.setReservationStatus(ReservationDto.RES_STATUS_CANCELED);

        //3. log history
        ReservationHistoryDto reservationLog = new ReservationHistoryDto(existing.getReversedBy(),
                existing.getReversionId(), ReservationHistoryDto.ACTION_CANCEL, new Date(), request.reason);
        revervationHistoryRepository.save(reservationLog);

        return reservationRepository.save(existing);
    }

    public ReservationDto updateReservation(CampsiteReservationModificationReqModel request) throws ReservationException{
        ReservationDto existing = reservationRepository.findById(request.reservationId).orElse(null);
        if(existing == null){
            throw new ReservationException("Cannot update, invalid reversaiont id: " + request.reservationId);
        }

        Date now = DateUtil.normalizeDay( new Date() );
        if(now.after(existing.getStartDay())){
            throw new ReservationException("Too late to update");
        }

        UserDto user = userService.findByEmail(request.email);
        if(user.getUserId() != existing.getReversedBy()){
            throw new ReservationException("Cannot update, email address does not match reservation id, reservation id: "
                    + request.reservationId + " email address: " + request.email);
        }
        return update(request, existing);
    }

    @Transactional
    public ReservationDto update(CampsiteReservationModificationReqModel request, ReservationDto existing ) throws ReservationException{
        //1. Make reservation canceled
        existing.setStartDay(DateUtil.parseDateNowOnError(request.startDay));
        existing.setEndDay(DateUtil.parseDateNowOnError(request.endDay));
        existing = reservationRepository.save(existing);

        //2. mark available
        campScheduleService.markAvailable(request.reservationId);
        //3. update
        campScheduleService.reserve(existing, existing.getStartDay(), existing.getEndDay());

        //4. log history
        ReservationHistoryDto reservationLog = new ReservationHistoryDto(existing.getReversedBy(),
                existing.getReversionId(), ReservationHistoryDto.ACTION_UPDATE, new Date(), request.reason);
        revervationHistoryRepository.save(reservationLog);

        return existing;
    }
}
