package com.uexcel.bookingservice.command;

import com.uexcel.common.BookingStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Data
public class CreateBookingCommand {
    @TargetAggregateIdentifier
    private final String bookingId;
    private final LocalDate bookingDate;
    private final String customerName;
    private final String mobileNumber;
    private final int numberOfRoom;
    private final int numberOfDays;
    private final String roomTypeId;
    private final BookingStatus bookingStatus;
}
