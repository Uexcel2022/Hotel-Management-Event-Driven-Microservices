package com.uexcel.reservationservice.command.controller;
import lombok.Data;

import java.time.LocalDate;
@Data
public class UpdateReservationModel {
    private String reservationId;
    private String customerName;
}
