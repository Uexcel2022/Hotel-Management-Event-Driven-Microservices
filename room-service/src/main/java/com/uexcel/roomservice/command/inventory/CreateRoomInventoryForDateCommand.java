package com.uexcel.roomservice.command.inventory;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
@Data
@Builder
public class CreateRoomInventoryForDateCommand {
    @TargetAggregateIdentifier
    private String roomInventoryForDateId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate availabilityDate;
    private String roomTypeName;
    private double price;
}
