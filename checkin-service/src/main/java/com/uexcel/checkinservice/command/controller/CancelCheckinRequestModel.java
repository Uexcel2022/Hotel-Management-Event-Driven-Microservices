package com.uexcel.checkinservice.command.controller;

import lombok.Data;

@Data
public class CancelCheckinRequestModel {
    private String checkinId;
    private String roomNumber;
}
