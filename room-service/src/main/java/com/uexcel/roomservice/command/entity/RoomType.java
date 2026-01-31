package com.uexcel.roomservice.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class RoomType {
    @Id
    private String roomTypeId;
    private String roomTypeName;
    private int quantity;
    private double price;
}





