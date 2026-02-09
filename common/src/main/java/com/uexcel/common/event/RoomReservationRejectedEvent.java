package com.uexcel.common.event;

import lombok.Data;

import java.time.LocalDate;


@Data
public class RoomReservationRejectedEvent {
    private String roomInventoryForDateId;
    private String roomTypeId;
    private String reservationId;
    private int bookedQuantity;
    private LocalDate bookingDate;
    private String reason;
}
