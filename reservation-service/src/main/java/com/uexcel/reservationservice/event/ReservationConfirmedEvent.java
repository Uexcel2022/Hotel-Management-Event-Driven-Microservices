package com.uexcel.reservationservice.event;

import com.uexcel.common.ReservationStatus;
import com.uexcel.common.event.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ReservationConfirmedEvent {
    private String reservationId;
    private String roomTypeName;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int bookedQuantity;
    private double price;
    private double total;
    private PaymentStatus paymentStatus;
    private ReservationStatus reservationStatus;
}
