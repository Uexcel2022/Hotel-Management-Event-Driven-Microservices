package com.uexcel.common.query;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoomModelForCheckinQuery {
    private final String roomNumber;
    private final String roomTypeName;
    private final String roomTypeId;
    private final String roomInventoryForDateId;
    private final double price;
}
