package com.uexcel.reservationservice.command;
import com.uexcel.common.BookingStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Aggregate
public class ReservationAggregate {
    @AggregateIdentifier
    private String reservationId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int bookedQuantity;
    private double price;
    private String roomTypeId;
    private String roomTypeName;
    private BookingStatus bookingStatus;


    public ReservationAggregate(){}

    @CommandHandler
    public ReservationAggregate(CreateReservationCommand command){
        ReservationCreatedEvent reservationCreatedEvent = new ReservationCreatedEvent();
        BeanUtils.copyProperties(command, reservationCreatedEvent);
        AggregateLifecycle.apply(reservationCreatedEvent);
    }

    @CommandHandler
    public void on(CancelReservationCommand command){
        ReservationCanceledEvent canceledEvent = new ReservationCanceledEvent();
        BeanUtils.copyProperties(command,canceledEvent);
        AggregateLifecycle.apply(canceledEvent);
    }

     @CommandHandler
    public void on(CreateConfirmReservationCommand command){
        ReservationConfirmedEvent reservationConfirmedEvent = ReservationConfirmedEvent.builder()
                .reservationId(command.getReservationId())
                .roomTypeName(command.getRoomTypeName())
                .bookingStatus(BookingStatus.APPROVED)
                .build();
        AggregateLifecycle.apply(reservationConfirmedEvent);
    }

    @EventSourcingHandler
    public void on(ReservationCreatedEvent event){
        this.reservationId= event.getReservationId();
        this.roomTypeId = event.getRoomTypeId();
        this.bookingDate = event.getBookingDate();
        this.bookedQuantity = event.getBookedQuantity();
        this.customerName= event.getCustomerName();
        this.mobileNumber = event.getMobileNumber();
        this.roomTypeName = event.getRoomTypeName();
        this.price = event.getPrice();
    }
    @EventSourcingHandler
    public  void on(ReservationCanceledEvent event){
        this.bookingStatus = event.getBookingStatus();
    }

    @EventSourcingHandler
    public void on(ReservationConfirmedEvent event){
        this.bookingStatus = event.getBookingStatus();
        event.setBookingDate(this.bookingDate);
        event.setBookedQuantity(this.bookedQuantity);
        event.setCustomerName(this.customerName);
        event.setMobileNumber(this.mobileNumber);
        event.setRoomTypeName(this.roomTypeName);
        event.setBookingStatus(this.bookingStatus);
    }
}
