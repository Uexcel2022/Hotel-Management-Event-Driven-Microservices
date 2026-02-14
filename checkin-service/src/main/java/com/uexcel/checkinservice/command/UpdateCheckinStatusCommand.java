package com.uexcel.checkinservice.command;

import com.uexcel.checkinservice.CheckinStatus;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class UpdateCheckinStatusCommand {
    @TargetAggregateIdentifier
    String checkinId;
    CheckinStatus checkinStatus;
}
