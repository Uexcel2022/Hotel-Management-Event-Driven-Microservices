package com.uexcel.reservationservice.command;

import com.uexcel.common.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class PaymentUpdateCommand {
    @TargetAggregateIdentifier
    private final String reservationId;
    private final PaymentStatus paymentStatus;

}
