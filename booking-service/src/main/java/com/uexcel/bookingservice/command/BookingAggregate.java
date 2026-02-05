package com.uexcel.bookingservice.command;
import com.uexcel.common.BookingStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;

@Aggregate
public class BookingAggregate {
    @AggregateIdentifier
    private String bookingId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int numberOfRoom;
    private int numberOfDays;
    private String roomTypeId;
    private BookingStatus bookingStatus;

    public BookingAggregate(){}

    @CommandHandler
    public BookingAggregate(CreateBookingCommand command){
        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent();
        BeanUtils.copyProperties(command,bookingCreatedEvent);
        AggregateLifecycle.apply(bookingCreatedEvent);
    }

    @CommandHandler
    public void on(CancelBookingCommand command){
        BookingCanceledEvent canceledEvent = new BookingCanceledEvent();
        BeanUtils.copyProperties(command,canceledEvent);
        AggregateLifecycle.apply(canceledEvent);
    }

    @EventSourcingHandler
    public void on(BookingCreatedEvent event){
        this.bookingId = event.getBookingId();
        this.roomTypeId = event.getRoomTypeId();
        this.bookingDate = event.getBookingDate();
        this.numberOfRoom = event.getNumberOfRoom();
        this.numberOfDays = event.getNumberOfDays();
        this.customerName= event.getCustomerName();
        this.mobileNumber = event.getMobileNumber();
    }
    @EventSourcingHandler
    public  void on(BookingCanceledEvent event){
        this.bookingStatus = event.getBookingStatus();
    }
}
