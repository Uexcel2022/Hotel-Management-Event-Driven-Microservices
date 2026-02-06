package com.uexcel.reservationservice.command;


import com.uexcel.common.BookingStatus;

import lombok.Data;


import java.time.LocalDate;

@Data
public class ReservationCanceledEvent {
    private String reservationId;
    private LocalDate bookingDate;
    private String customerName;
    private String mobileNumber;
    private int bookedQuantity;
    private String roomTypeId;
    private BookingStatus bookingStatus;
    private String  reason;
}
