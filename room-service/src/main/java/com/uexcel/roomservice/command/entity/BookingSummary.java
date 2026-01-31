package com.uexcel.roomservice.command.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class BookingSummary {
    @Id
    private String bookingSummaryId;
    private String roomTypeId;
    private int bookedQuantity;
    public LocalDate bookingDate;
}
