package com.uexcel.bookingservice.command;


import com.uexcel.common.BookingStatus;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

@Data
@Builder
public class CancelBookingCommand {
    @TargetAggregateIdentifier
    private String bookingId;
    private LocalDate bookingDate;
    private String customerName;
    private String mobileNumber;
    private int numberOfRoom;
    private int numberOfDays;
    private String roomTypeId;
    private BookingStatus bookingStatus;
    private String reason;
}
