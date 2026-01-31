package com.uexcel.roomservice.command.room;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class RoomAggregate {
    @AggregateIdentifier
    private String number;
    private String roomTypeId;
    public RoomAggregate(){}

    @CommandHandler
    public RoomAggregate(CreateRoomCommand cmd){
        RoomCreatedEvent event = new RoomCreatedEvent();
        BeanUtils.copyProperties(cmd,event);
        AggregateLifecycle.apply(event);
    }

    @EventSourcingHandler
    public void  on(RoomCreatedEvent event){
        this.number = event.getNumber();
        this.roomTypeId = event.getRoomTypeId();
    }
}
