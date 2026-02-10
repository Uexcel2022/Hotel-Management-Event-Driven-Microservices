package com.uexcel.roomservice.command.inventory;

import lombok.Data;

import java.time.LocalDate;
@Data
public class RoomInventoryForDateCreatedEvent {
    private  String roomInventoryForDateId;
    private String roomTypeId;
    private String roomTypeName;
    private LocalDate availabilityDate;
    private int availableRooms;
    private double price;
}
