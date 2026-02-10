package com.uexcel.reservationservice.event;

import com.uexcel.common.ReservationStatus;
import com.uexcel.common.PaymentStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationCreatedEvent {
    private String reservationId;
    private String roomInventoryForDateId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookedDate;
    private String roomTypeId;
    private double price;
    private double total;
    private String roomTypeName;
    private PaymentStatus paymentStatus;
    private ReservationStatus reservationStatus;

}