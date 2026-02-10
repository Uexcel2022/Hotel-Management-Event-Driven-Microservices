package com.uexcel.reservationservice.event;


import com.uexcel.common.ReservationStatus;

import com.uexcel.common.PaymentStatus;
import lombok.Data;


import java.time.LocalDate;

@Data
public class ReservationCanceledEvent {
    private String reservationId;
    private LocalDate bookedDate;
    private String roomTypeName;
    private String customerName;
    private String mobileNumber;
    private String roomTypeId;
    private double price;
    private double total;
    private ReservationStatus reservationStatus;
    private PaymentStatus paymentStatus;
    private String  reason;

}
