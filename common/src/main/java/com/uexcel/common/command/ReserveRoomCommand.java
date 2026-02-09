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
    private final int bookedQuantity;
    private final LocalDate bookingDate;
    private final String roomTypeName;
    private final double price;
    private final String customerName;
    private String mobileNumber;
}
