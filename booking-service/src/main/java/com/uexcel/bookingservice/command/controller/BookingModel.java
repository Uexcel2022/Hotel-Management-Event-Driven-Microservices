package com.uexcel.bookingservice.command.controller;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class BookingModel {
    private LocalDate bookingDate;
    private String customerName;
    private String mobileNumber;
    private int numberOfRoom;
    private int numberOfDays;
    private String roomTypeId;
}
