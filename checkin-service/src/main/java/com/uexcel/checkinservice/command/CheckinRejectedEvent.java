package com.uexcel.checkinservice.command;

import com.uexcel.checkinservice.CheckinStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CheckinRejectedEvent {
    String checkinId;
    String reason;
    CheckinStatus checkinStatus;
}
