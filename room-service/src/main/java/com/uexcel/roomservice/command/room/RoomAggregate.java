package com.uexcel.roomservice.command.room;

import com.uexcel.common.RoomStatus;
import com.uexcel.common.command.UpdateRoomForCheckinCommand;
import com.uexcel.roomservice.query.checkin.RoomStatusUpdatedForCheckinEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class RoomAggregate {
    @AggregateIdentifier
    private String roomNumber;
    private String roomTypeId;
    private RoomStatus roomStatus;
    public RoomAggregate(){}

    @CommandHandler
    public RoomAggregate(CreateRoomCommand cmd){
        RoomCreatedEvent event = new RoomCreatedEvent();
        BeanUtils.copyProperties(cmd,event);
        AggregateLifecycle.apply(event);
    }

    @CommandHandler
    public void on(UpdateRoomForCheckinCommand command){
        RoomStatusUpdatedForCheckinEvent  event =
                new RoomStatusUpdatedForCheckinEvent();
        BeanUtils.copyProperties(command,event);
        AggregateLifecycle.apply(event);

    }

    @EventSourcingHandler
    public void  on(RoomCreatedEvent event){
        this.roomNumber = event.getRoomNumber();
        this.roomTypeId = event.getRoomTypeId();
        this.roomStatus = event.getRoomStatus();
    }

    @EventSourcingHandler
    public void  on(RoomStatusUpdatedForCheckinEvent event){
        this.roomStatus = event.getRoomStatus();
    }
}
