package com.uexcel.reservationservice.command;

import com.uexcel.common.BookingStatus;
import lombok.Data;
import java.time.LocalDate;

@Data
public class ReservationCreatedEvent {
    private String reservationId;
    private String roomInventoryForDateId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int bookedQuantity;
    private String roomTypeId;
    private double price;
    private String roomTypeName;
    private BookingStatus bookingStatus;

}