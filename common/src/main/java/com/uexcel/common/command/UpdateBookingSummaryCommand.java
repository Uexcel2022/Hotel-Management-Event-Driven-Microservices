package com.uexcel.common.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
@Data
public class UpdateBookingSummaryCommand {
    @TargetAggregateIdentifier
    private final String roomTypeId;
    private final int bookedQuantity;
    private final LocalDate date;
}
