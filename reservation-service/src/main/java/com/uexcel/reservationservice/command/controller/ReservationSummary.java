package com.uexcel.reservationservice.command.controller;

import com.uexcel.common.BookingStatus;
import lombok.Data;

@Data
public class ReservationSummary {
    private String reservationId;
    private BookingStatus bookingStatus;
    private String reason;
}
