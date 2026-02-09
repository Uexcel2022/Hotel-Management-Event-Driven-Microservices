package com.uexcel.roomservice.query.controller;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomInventoryForDateModel {
    private String roomInventoryForDateId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate bookingDate;
    private String roomTypeName;
    private double price;
}
