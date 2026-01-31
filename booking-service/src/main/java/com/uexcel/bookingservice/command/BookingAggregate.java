package com.uexcel.bookingservice.command;

import com.uexcel.common.command.CreateBookingCommand;
import com.uexcel.common.event.BookingCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalTime;
@Aggregate
public class BookingAggregate {
    @AggregateIdentifier
    private String bookingId;
    private String type;
    private LocalDate date;
    private int numberOfRoom;
    private LocalTime numberOfDays;

    public BookingAggregate(){}

    @CommandHandler
    public BookingAggregate(CreateBookingCommand command){
        BookingCreatedEvent bookingCreatedEvent = new BookingCreatedEvent();
        BeanUtils.copyProperties(command,bookingCreatedEvent);
        AggregateLifecycle.apply(bookingCreatedEvent);
    }

    @EventSourcingHandler
    public void on(BookingCreatedEvent event){
        this.bookingId = event.getBookingId();
        this.type = event.getType();
        this.date = event.getDate();
        this.numberOfRoom = event.getNumberOfRoom();
        this.numberOfDays = event.getNumberOfDays();
    }
}
