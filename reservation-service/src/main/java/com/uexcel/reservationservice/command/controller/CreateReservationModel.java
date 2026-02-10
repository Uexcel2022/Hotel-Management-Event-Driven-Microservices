package com.uexcel.reservationservice.command.controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateReservationModel {
    private String roomInventoryForDateId;
    private LocalDate bookedDate;
    private String customerName;
    private String mobileNumber;
    private String roomTypeId;
    private String roomTypeName;
    private double price;
}
