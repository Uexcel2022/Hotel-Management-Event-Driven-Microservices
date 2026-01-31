package com.uexcel.bookingservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class CreateBookingCommand {
    @TargetAggregateIdentifier
    private final String type;
    private final LocalDate date;
    private final int numberOfRoom;
    private final LocalTime numberOfDays;
}
