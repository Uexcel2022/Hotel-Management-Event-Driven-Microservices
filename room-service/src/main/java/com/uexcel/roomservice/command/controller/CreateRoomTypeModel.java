package com.uexcel.roomservice.command.controller;

import lombok.Data;

@Data
public class CreateRoomTypeModel {
    private String roomTypeName;
    private int quantity;
    private double price;
}
