package com.uexcel.bookingservice.command.controller;

import com.uexcel.common.BookingStatus;
import lombok.Data;

@Data
public class BookingSummary {
    private String bookingId;
    private BookingStatus bookingStatus;
    private String reason;
}
