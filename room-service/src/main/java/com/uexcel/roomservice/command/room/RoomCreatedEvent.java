package com.uexcel.roomservice.command.room;

import lombok.Data;

@Data
public class RoomCreatedEvent {
    private String number;
    private String roomTypeId;
}
