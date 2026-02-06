package com.uexcel.roomservice.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class RoomInventoryForDate {
    @Id
    private String roomInventoryForDateId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate bookingDate;
    private String roomTypeName;
    private double price;

}

