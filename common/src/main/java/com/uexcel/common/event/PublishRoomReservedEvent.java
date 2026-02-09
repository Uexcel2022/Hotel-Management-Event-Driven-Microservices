package com.uexcel.common.event;

import com.uexcel.common.ReservationStatus;
import lombok.Data;

import java.time.LocalDate;
@Data
public class PublishRoomReservedEvent {
    private String reservationId;
    private String roomInventoryForDateId;
    private String customerName;
    private String mobileNumber;
    private LocalDate bookingDate;
    private int numberOfRoom;
    private int numberOfDays;
    private String roomTypeId;
    private double price;
    private String roomTypeName;
    private ReservationStatus reservationStatus;
}
