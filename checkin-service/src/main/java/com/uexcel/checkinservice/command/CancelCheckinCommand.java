package com.uexcel.checkinservice.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelCheckinCommand {
    @TargetAggregateIdentifier
    String checkinId;
    String roomNumber;
}
