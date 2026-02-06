package com.uexcel.common.event;

import lombok.Data;

import java.time.LocalDate;


@Data
public class RoomReservationRejectedEvent {
    private final String roomInventoryForDateId;
    private final String roomTypeId;
    private final String reservationId;
    private final int bookedQuantity;
    private final LocalDate bookingDate;
    private final String reason;
}
