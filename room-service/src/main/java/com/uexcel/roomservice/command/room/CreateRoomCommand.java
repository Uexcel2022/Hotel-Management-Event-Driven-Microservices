package com.uexcel.roomservice.command.room;

import com.uexcel.common.RoomStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateRoomCommand {
    @TargetAggregateIdentifier
    private final String roomNumber;
    private final String roomTypeId;
    private final RoomStatus roomStatus;
}
