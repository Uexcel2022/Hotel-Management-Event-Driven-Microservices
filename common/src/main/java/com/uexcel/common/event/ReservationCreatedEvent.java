package com.uexcel.common.event;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
public class ReservationCreatedEvent {
    private  String reservationId;
    private String roomTypeId;
    private String bookingId;
    private int bookedQuantity;
    private LocalDate bookingDate;
    private int checkinCount;
}
