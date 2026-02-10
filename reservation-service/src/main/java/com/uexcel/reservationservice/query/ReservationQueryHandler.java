package com.uexcel.reservationservice.query;

import com.uexcel.reservationservice.command.entity.Reservation;
import com.uexcel.reservationservice.command.repository.ReservationRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReservationQueryHandler {
    private final ReservationRepository reservationRepository;

    public ReservationQueryHandler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @QueryHandler
    public ReservationSummary on(FindReservationQuery query){
        ReservationSummary reservationSummary = new ReservationSummary();
        Reservation reservation = reservationRepository
                .findByReservationId(query.getReservationId());
        BeanUtils.copyProperties(reservation, reservationSummary);
        return reservationSummary;
    }

     @QueryHandler
    public List<ReservationSummary> on(FindReservationByMobileNumberQuery query){
       List<ReservationSummary> reservationSummaryList = new ArrayList<>();
        reservationRepository.findByMobileNumber(query.getMobileNumber())
                .forEach(reservation -> {
                    ReservationSummary reservationSummary = new ReservationSummary();
                        BeanUtils.copyProperties(reservation, reservationSummary);
                        reservationSummaryList.add(reservationSummary);
                });
        return reservationSummaryList;
    }

}
