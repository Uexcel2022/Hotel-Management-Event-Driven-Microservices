package com.uexcel.common.command;

import com.uexcel.common.RoomStatus;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class UpdateRoomForCheckinCommand {
    @TargetAggregateIdentifier
    String roomNumber;
    RoomStatus roomStatus;
}
