package com.uexcel.reservationservice.command;

import com.uexcel.common.ReservationStatus;
import com.uexcel.common.event.PaymentStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class CreateReservationCommand {
    @TargetAggregateIdentifier
    private final String reservationId;
    private final String roomInventoryForDateId;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate bookingDate;
    private final String customerName;
    private final String mobileNumber;
    private final int bookedQuantity;
    private final String roomTypeId;
    private final String roomTypeName;
    private final double price;
    private final double total;
    private PaymentStatus paymentStatus;
    private final ReservationStatus reservationStatus;

}
