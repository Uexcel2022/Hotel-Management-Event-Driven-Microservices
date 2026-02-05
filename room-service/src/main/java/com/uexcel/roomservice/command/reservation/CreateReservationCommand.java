package com.uexcel.roomservice.command.reservation;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class CreateReservationCommand {
    private String reservationId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate bookingDate;
}
