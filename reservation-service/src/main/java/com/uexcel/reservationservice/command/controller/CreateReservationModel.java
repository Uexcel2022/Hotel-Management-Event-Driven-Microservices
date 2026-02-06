package com.uexcel.reservationservice.command.controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateReservationModel {
    private String roomInventoryForDateId;
    private LocalDate bookingDate;
    private String customerName;
    private String mobileNumber;
    private int bookedQuantity;
    private String roomTypeId;
    private String roomTypeName;
    private double price;
}
