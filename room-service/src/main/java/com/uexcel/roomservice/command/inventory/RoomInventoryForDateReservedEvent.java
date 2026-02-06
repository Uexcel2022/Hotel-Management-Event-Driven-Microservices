package com.uexcel.roomservice.command.inventory;

import com.uexcel.common.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class RoomInventoryForDateReservedEvent {
    private String roomInventoryForDateId;
    private String reservationId;
    private String roomTypeId;
    private int bookedQuantity;
    private LocalDate bookingDate;
    private String roomTypeName;
    private double price;
    private String customerName;
    private BookingStatus bookingStatus;
}
