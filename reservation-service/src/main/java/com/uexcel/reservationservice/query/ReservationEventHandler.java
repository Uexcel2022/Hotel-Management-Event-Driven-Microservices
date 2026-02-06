package com.uexcel.reservationservice.query;

import com.uexcel.reservationservice.command.ReservationConfirmedEvent;
import com.uexcel.reservationservice.command.ReservationCreatedEvent;
import com.uexcel.reservationservice.command.ReservationCanceledEvent;
import com.uexcel.reservationservice.command.PublishReservationConfirmedEvent;
import com.uexcel.reservationservice.command.entity.Reservation;
import com.uexcel.reservationservice.command.repository.ReservationRepository;
import com.uexcel.common.BookingStatus;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.GenericEventMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("reservation-group")
public class ReservationEventHandler {
    private final ReservationRepository reservationRepository;

    public ReservationEventHandler(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
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
        reservation.setBookingStatus(BookingStatus.REJECTED);
        reservationRepository.save(reservation);
    }

    @EventHandler
    public void on(ReservationConfirmedEvent event, EventBus eventBus){
        Reservation reservation =
                reservationRepository.findByReservationId(event.getReservationId());
        if(reservation ==null){
            throw new IllegalStateException("Reservation not found.");
        }
        reservation.setBookingStatus(BookingStatus.APPROVED);
        reservationRepository.save(reservation);
        PublishReservationConfirmedEvent pRCE = new PublishReservationConfirmedEvent();
        BeanUtils.copyProperties(event, pRCE);
        eventBus.publish(GenericEventMessage.asEventMessage(pRCE));
    }

}
