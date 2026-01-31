package com.uexcel.roomservice.command.roomtype;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CreateRoomTypeCommand {
    @TargetAggregateIdentifier
    private final String roomTypeId;
    private final String roomTypeName;
    private final int quantity;
    private final double price;
}
