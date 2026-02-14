package com.uexcel.roomservice.query.checkin;

import com.uexcel.common.RoomStatus;
import lombok.Data;

@Data
public class RoomStatusUpdatedForCheckinEvent {
    private RoomStatus roomStatus;
    private String roomNumber;


}
