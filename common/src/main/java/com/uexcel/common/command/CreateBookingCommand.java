package com.uexcel.common.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class CreateBookingCommand {
    @TargetAggregateIdentifier
    private final String bookingId;
    private final LocalDate date;
    private final int numberOfRoom;
    private final LocalTime numberOfDays;
    private final String roomTypeId;
}
