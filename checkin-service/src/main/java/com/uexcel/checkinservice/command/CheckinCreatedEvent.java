package com.uexcel.checkinservice.command;


import lombok.Data;

import java.time.LocalDateTime;


@Data
public class CheckinCreatedEvent {
    private String checkinId;
    private String paymentId;
    private String roomNumber;
    private String customerName;
    private String mobileNumber;
    private String nextOfKinMobileNumber;
    private String roomTypeName;
    private String roomInventoryForDateId;
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;
    private String reservationId;
    private double paid;
}

