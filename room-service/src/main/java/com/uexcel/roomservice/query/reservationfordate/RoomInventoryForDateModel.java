package com.uexcel.roomservice.query.reservationfordate;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RoomInventoryForDateModel {
    private String roomInventoryForDateId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate availabilityDate;
    private String roomTypeName;
    private double price;
}
