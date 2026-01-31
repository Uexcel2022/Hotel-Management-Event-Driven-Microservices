package com.uexcel.common.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class BookingCreatedEvent {
    private String bookingId;
    private String type;
    private LocalDate date;
    private int numberOfRoom;
    private LocalTime numberOfDays;
}