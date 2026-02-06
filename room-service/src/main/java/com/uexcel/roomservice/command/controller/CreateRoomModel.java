package com.uexcel.roomservice.command.controller;

import lombok.Data;

@Data
public class CreateRoomModel {
    private String roomNumber;
    private String roomTypeId;
}
