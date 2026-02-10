package com.uexcel.reservationservice.command;
import com.uexcel.common.ReservationStatus;
import com.uexcel.common.PaymentStatus;
import com.uexcel.reservationservice.event.PaymentUpdatedEvent;
import com.uexcel.reservationservice.event.ReservationCanceledEvent;
import com.uexcel.reservationservice.event.ReservationConfirmedEvent;
import com.uexcel.reservationservice.event.ReservationCreatedEvent;
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
    private double price;
    private String roomTypeId;
    private String roomTypeName;
    private double total;
    private PaymentStatus paymentStatus;

    private ReservationStatus reservationStatus;


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
    public void on(ConfirmReservationCommand command){
        ReservationConfirmedEvent reservationConfirmedEvent = new ReservationConfirmedEvent();
                BeanUtils.copyProperties(command,reservationConfirmedEvent);
                reservationConfirmedEvent.setReservationStatus(ReservationStatus.approved);
        AggregateLifecycle.apply(reservationConfirmedEvent);
    }

    @CommandHandler
    public void on(PaymentUpdateCommand command){
        PaymentUpdatedEvent paymentUpdatedEvent = new PaymentUpdatedEvent();
        BeanUtils.copyProperties(command,paymentUpdatedEvent);
        AggregateLifecycle.apply(paymentUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(ReservationCreatedEvent event){
        this.reservationId= event.getReservationId();
        this.roomTypeId = event.getRoomTypeId();
        this.bookingDate = event.getBookedDate();
        this.customerName= event.getCustomerName();
        this.mobileNumber = event.getMobileNumber();
        this.roomTypeName = event.getRoomTypeName();
        this.price = event.getPrice();
        this.total = event.getTotal();
        this.reservationStatus = ReservationStatus.approved;
    }
    @EventSourcingHandler
    public  void on(ReservationCanceledEvent event){
        this.reservationStatus = event.getReservationStatus();
    }

    @EventSourcingHandler
    public void on(ReservationConfirmedEvent event){
        this.reservationStatus = event.getReservationStatus();
        this.reservationId = event.getReservationId();
        this.roomTypeName = event.getRoomTypeName();
        this.customerName = event.getCustomerName();
        this.mobileNumber = event.getMobileNumber();
        this.bookingDate = event.getBookedDate();
        this.price = event.getPrice();
    }

    @EventSourcingHandler
    public void on(PaymentUpdatedEvent event){
        this.paymentStatus = event.getPaymentStatus();
    }
}
