package com.uexcel.common.event;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ReservationUpdatedEvent {
    private String reservationId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate bookingDate;
}
