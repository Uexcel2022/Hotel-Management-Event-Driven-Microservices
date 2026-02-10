package com.uexcel.reservationservice.event;

import com.uexcel.common.ReservationStatus;
import com.uexcel.common.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ReservationConfirmedEvent {
    private String reservationId;
    private String roomTypeName;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookedDate;
    private double price;
    private double total;
    private PaymentStatus paymentStatus;
    private ReservationStatus reservationStatus;
}
