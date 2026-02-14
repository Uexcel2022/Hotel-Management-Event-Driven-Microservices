package com.uexcel.checkinservice.command;

import com.uexcel.checkinservice.CheckinStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CheckinCalledEvent {
    private String checkinId;
    private String roomNumber;
    private CheckinStatus checkinStatus;
}
