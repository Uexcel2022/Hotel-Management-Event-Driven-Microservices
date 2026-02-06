package com.uexcel.reservationservice.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CreateConfirmReservationCommand {
    @TargetAggregateIdentifier
    String reservationId;
    String roomTypeName;
}
