package com.uexcel.roomservice.command.roomtype;

import lombok.Builder;
import lombok.Data;

@Data
public class RoomTypeCreatedEvent {
    private String roomTypeId;
    private String roomTypeName;
    private int quantity;
    private double price;
}
