package com.uexcel.roomservice.query.room;

import com.uexcel.common.RoomStatus;

import lombok.Data;

@Data
public class RoomModel {
    private String roomNumber;
    private String roomTypeId;
    private RoomStatus roomStatus;
}
