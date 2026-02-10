package com.uexcel.roomservice.query.room;

import com.uexcel.common.RoomStatus;
import lombok.Value;

@Value
public class FindRoomByRoomTypeIdAndRoomStatus {
    String roomTypeId;
    RoomStatus roomStatus;
}
