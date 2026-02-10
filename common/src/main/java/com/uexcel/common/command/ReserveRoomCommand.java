package com.uexcel.common.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


import java.time.LocalDate;
@Data
@Builder
public class ReserveRoomCommand {
    @TargetAggregateIdentifier
    private final String roomInventoryForDateId;
    private final String roomTypeId;
    private final String reservationId;
    private final LocalDate bookedDate;
    private final String roomTypeName;
    private final double price;
    private final double total;
    private final String customerName;
    private String mobileNumber;
}
