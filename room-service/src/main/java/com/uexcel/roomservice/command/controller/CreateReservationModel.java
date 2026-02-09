package com.uexcel.roomservice.command.controller;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CreateReservationModel {
    private String roomTypeId;
    private LocalDate startDate;
    private int numberOfDaysAhead;
}
