package com.uexcel.checkinservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDateTime;

@Builder
@Data
public class CreateCheckinCommand {
    @TargetAggregateIdentifier
    private final String checkinId;
    private final String paymentId;
    private final String roomNumber;
    private final String customerName;
    private final String mobileNumber;
    private final String nextOfKinMobileNumber;
    private final String roomTypeName;
    private final String roomInventoryForDateId;
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;
    private String reservationId;
    private final double paid;
}

