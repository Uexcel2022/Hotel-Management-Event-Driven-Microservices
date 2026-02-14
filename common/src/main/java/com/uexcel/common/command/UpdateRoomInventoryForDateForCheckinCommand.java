package com.uexcel.common.command;

import com.uexcel.common.RoomStatus;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class UpdateRoomInventoryForDateForCheckinCommand {
    @TargetAggregateIdentifier
    String roomInventoryForDateId;
    String reservationId;
    String roomTypeName;
    String roomNumber;
}
