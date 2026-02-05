package com.uexcel.common.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReservationResponse {
    private boolean status;
    private String message;
}
