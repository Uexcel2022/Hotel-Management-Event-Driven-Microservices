package com.uexcel.reservationservice.command;

import com.uexcel.common.ReservationStatus;
import com.uexcel.common.PaymentStatus;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

@Data
public class ConfirmReservationCommand {
    @TargetAggregateIdentifier
    private String reservationId;
    private String roomTypeName;
    private String roomInventoryForDateId;
    private LocalDate bookedDate;
    private String customerName;
    private String mobileNumber;
    private String roomTypeId;
    private String reason;
    private double price;
    private double total;
    private PaymentStatus paymentStatus;
    private ReservationStatus reservationStatus;
}
