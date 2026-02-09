package com.uexcel.reservationservice.query;

import com.uexcel.reservationservice.event.PaymentUpdatedEvent;
import com.uexcel.reservationservice.event.ReservationConfirmedEvent;
import com.uexcel.reservationservice.event.ReservationCreatedEvent;
import com.uexcel.reservationservice.event.ReservationCanceledEvent;

import com.uexcel.reservationservice.command.entity.Reservation;
import com.uexcel.reservationservice.command.repository.ReservationRepository;
import com.uexcel.common.ReservationStatus;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;

import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("reservation-group")
public class ReservationEventHandler {
    private final ReservationRepository reservationRepository;
    private final QueryUpdateEmitter queryUpdateEmitter;

    public ReservationEventHandler(ReservationRepository reservationRepository,
                                   QueryUpdateEmitter queryUpdateEmitter) {
        this.reservationRepository = reservationRepository;
        this.queryUpdateEmitter = queryUpdateEmitter;
    }
    @EventHandler
    public void on(ReservationCreatedEvent event){
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(event, reservation);
        reservationRepository.save(reservation);
    }

    @EventHandler
    public void on(ReservationCanceledEvent event){
        Reservation reservation =
                reservationRepository.findByReservationId(event.getReservationId());
        if(reservation ==null){
            throw new IllegalStateException("Reservation not found.");
        }
        reservation.setReservationStatus(ReservationStatus.rejected);
        reservationRepository.save(reservation);

    }

    @EventHandler
    public void on(ReservationConfirmedEvent event){
        Reservation reservation =
                reservationRepository.findByReservationId(event.getReservationId());
        if(reservation ==null){
            throw new IllegalStateException("Reservation not found.");
        }
        reservation.setReservationStatus(ReservationStatus.approved);
        reservationRepository.save(reservation);

    }

    @EventHandler
    public void on(PaymentUpdatedEvent event){
        Reservation reservation =
                reservationRepository.findByReservationId(event.getReservationId());
        if(reservation ==null){
            throw new IllegalStateException("Reservation not found.");
        }
        reservation.setPaymentStatus(event.getPaymentStatus());
        Reservation updatedReservation = reservationRepository.save(reservation);
        ReservationSummary reservationSummary = new ReservationSummary();
        BeanUtils.copyProperties(updatedReservation, reservationSummary);

        queryUpdateEmitter.emit(
                FindReservationQuery.class,
                q ->
                        q.getReservationId().equals(event.getReservationId()),
                reservationSummary
        );
    }

}
