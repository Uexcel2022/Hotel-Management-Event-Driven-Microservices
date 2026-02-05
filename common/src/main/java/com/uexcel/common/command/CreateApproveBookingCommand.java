package com.uexcel.common.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
@Data
@Builder
public class CreateApproveBookingCommand {
    @TargetAggregateIdentifier
    private String reservationId;
    private final String roomTypeId;
    private final String bookingId;
    private final int bookedQuantity;
    private final LocalDate bookingDate;
    private final int checkinCount;
}
