package com.uexcel.roomservice.command.inventory;

import com.uexcel.common.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class RoomReservedEvent {
    private String roomInventoryForDateId;
    private String reservationId;
    private String roomTypeId;
    private LocalDate bookedDate;
    private String roomTypeName;
    private double price;
    private String customerName;
    private ReservationStatus reservationStatus;
}
