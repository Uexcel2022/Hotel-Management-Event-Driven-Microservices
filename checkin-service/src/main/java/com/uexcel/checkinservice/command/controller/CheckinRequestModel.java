package com.uexcel.checkinservice.command.controller;


import lombok.Data;


@Data
public class CheckinRequestModel {
    private String paymentId;
    private String roomNumber;
    private String customerName;
    private String mobileNumber;
    private String nextOfKinMobileNumber;
    private String roomTypeName;
    private String roomInventoryForDateId;
    private String reservationId;
    private double paid;
}

