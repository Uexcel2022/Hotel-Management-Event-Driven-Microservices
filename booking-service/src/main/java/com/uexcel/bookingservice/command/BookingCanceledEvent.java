package com.uexcel.bookingservice.command;


import com.uexcel.common.BookingStatus;

import lombok.Data;


import java.time.LocalDate;

@Data
public class BookingCanceledEvent {
    private String bookingId;
    private LocalDate bookingDate;
    private String customerName;
    private String mobileNumber;
    private int numberOfRoom;
    private int numberOfDays;
    private String roomTypeId;
    private BookingStatus bookingStatus;
    private String  reason;
}
