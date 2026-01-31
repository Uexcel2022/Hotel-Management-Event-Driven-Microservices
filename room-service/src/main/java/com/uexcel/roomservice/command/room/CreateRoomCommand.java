package com.uexcel.roomservice.command.room;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateRoomCommand {
    @TargetAggregateIdentifier
    private final String number;
    private final String roomTypeId;
}
