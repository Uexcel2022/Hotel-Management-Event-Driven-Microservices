package com.uexcel.common.event;

import lombok.Data;

import java.time.LocalDate;


@Data
public class BookingNotApprovedEvent {
    private final String reservationId;
    private final String roomTypeId;
    private final String bookingId;
    private final int bookedQuantity;
    private final LocalDate bookingDate;
    private final int checkinCount;
    private final String reason;
}
