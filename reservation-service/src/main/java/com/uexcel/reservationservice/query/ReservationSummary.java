package com.uexcel.reservationservice.query;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.uexcel.common.ReservationStatus;
import com.uexcel.common.event.PaymentStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservationSummary {
    private String reservationId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int bookedQuantity;
    private double price;
    private double total;
    private PaymentStatus paymentStatus;
    private ReservationStatus reservationStatus;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String  reason;

}
