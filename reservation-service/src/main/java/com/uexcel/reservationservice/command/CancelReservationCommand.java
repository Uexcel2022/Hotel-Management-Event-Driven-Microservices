package com.uexcel.reservationservice.command;


import com.uexcel.common.ReservationStatus;
import com.uexcel.common.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

@Data
@Builder
public class CancelReservationCommand {
    @TargetAggregateIdentifier
    private final String reservationId;
    private final String roomInventoryForDateId;
    private final LocalDate bookedDate;
    private final String customerName;
    private final String roomTypeName;
    private final String mobileNumber;
    private final String roomTypeId;
    private final String reason;
    private final double price;
    private final double total;
    private final ReservationStatus reservationStatus;
    private final PaymentStatus paymentStatus;

}
