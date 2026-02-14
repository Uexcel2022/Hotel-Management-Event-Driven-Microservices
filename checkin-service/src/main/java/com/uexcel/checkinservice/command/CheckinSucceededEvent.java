package com.uexcel.checkinservice.command;

import com.uexcel.checkinservice.CheckinStatus;
import lombok.Data;
import lombok.Value;

@Data
public class CheckinSucceededEvent {
    String checkinId;
    CheckinStatus checkinStatus;
}
