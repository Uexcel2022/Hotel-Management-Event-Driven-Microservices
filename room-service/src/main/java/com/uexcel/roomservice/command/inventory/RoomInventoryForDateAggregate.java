package com.uexcel.roomservice.command.inventory;

import com.uexcel.common.ReservationStatus;
import com.uexcel.common.command.ReserveRoomCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


import java.time.LocalDate;

@Aggregate
public class RoomInventoryForDateAggregate {
    @AggregateIdentifier
    private String roomInventoryForDateId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate availabilityDate;
    private String roomTypeName;
    private double price;


    public RoomInventoryForDateAggregate() {
    }

    @CommandHandler
    public RoomInventoryForDateAggregate(CreateRoomInventoryForDateCommand command) {
        RoomInventoryForDateCreatedEvent roomInventoryForDateCreatedEvent = new RoomInventoryForDateCreatedEvent();
        BeanUtils.copyProperties(command, roomInventoryForDateCreatedEvent);
        AggregateLifecycle.apply(roomInventoryForDateCreatedEvent);
    }

    @CommandHandler
    public void on(ReserveRoomCommand command) {
        RoomReservedEvent roomReservedEvent = new RoomReservedEvent();
        BeanUtils.copyProperties(command, roomReservedEvent);
        roomReservedEvent.setReservationStatus(ReservationStatus.approved);
        AggregateLifecycle.apply(roomReservedEvent);
    }

    @EventSourcingHandler
    public void on(RoomInventoryForDateCreatedEvent event) {
        this.roomInventoryForDateId = event.getRoomInventoryForDateId();
        this.roomTypeId = event.getRoomTypeId();
        this.availabilityDate = event.getAvailabilityDate();
        this.availableRooms = event.getAvailableRooms();
        this.roomTypeName = event.getRoomTypeName();
    }

    @EventSourcingHandler
    public void on(RoomReservedEvent event) {
        this.availableRooms -= 1;
    }
}

