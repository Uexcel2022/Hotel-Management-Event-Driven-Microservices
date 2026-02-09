package com.uexcel.reservationservice.event;


import com.uexcel.common.ReservationStatus;

import com.uexcel.common.event.PaymentStatus;
import lombok.Data;


import java.time.LocalDate;

@Data
public class ReservationCanceledEvent {
    private String reservationId;
    private LocalDate bookingDate;
    private String roomTypeName;
    private String customerName;
    private String mobileNumber;
    private int bookedQuantity;
    private String roomTypeId;
    private double price;
    private double total;
    private ReservationStatus reservationStatus;
    private PaymentStatus paymentStatus;
    private String  reason;

}
