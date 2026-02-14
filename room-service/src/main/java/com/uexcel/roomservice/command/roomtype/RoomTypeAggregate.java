package com.uexcel.roomservice.command.roomtype;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class RoomTypeAggregate {
    @AggregateIdentifier
    private String roomTypeId;
    private String roomTypeName;
    private int quantity;
    private double price;
    public  RoomTypeAggregate() {}

    @CommandHandler
    public RoomTypeAggregate(CreateRoomTypeCommand command) {
        RoomTypeCreatedEvent roomCreatedEvent = new RoomTypeCreatedEvent();
        BeanUtils.copyProperties(command, roomCreatedEvent);
        AggregateLifecycle.apply(roomCreatedEvent);
    }
    @EventSourcingHandler
    public void on(RoomTypeCreatedEvent command) {
        this.roomTypeId = command.getRoomTypeId();
        this.roomTypeName = command.getRoomTypeName();
        this.quantity = command.getQuantity();
        this.price = command.getPrice();

    }
}
