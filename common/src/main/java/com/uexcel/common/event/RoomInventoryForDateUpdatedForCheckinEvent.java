package com.uexcel.common.event;

import lombok.Data;

@Data
public class RoomInventoryForDateUpdatedForCheckinEvent {
    private String roomInventoryForDateId;
    private int availableRooms;
}
