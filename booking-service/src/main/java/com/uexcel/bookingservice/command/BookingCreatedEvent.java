package com.uexcel.bookingservice.command;

import com.uexcel.common.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data
public class BookingCreatedEvent {
    private String bookingId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int numberOfRoom;
    private int numberOfDays;
    private String roomTypeId;
    private BookingStatus bookingStatus;
}