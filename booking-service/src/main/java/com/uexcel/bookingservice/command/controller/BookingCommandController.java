package com.uexcel.bookingservice.command.controller;

import com.uexcel.bookingservice.command.CreateBookingCommand;
import com.uexcel.bookingservice.query.FindBookingQuery;
import com.uexcel.common.BookingStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/bookings",produces = MediaType.APPLICATION_JSON_VALUE)
public class BookingCommandController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public BookingCommandController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/booking")
    public ResponseEntity<String> createBooking(@RequestBody BookingModel bookingModel) {
        CreateBookingCommand bookingCommand = CreateBookingCommand.builder()
                .bookingId(UUID.randomUUID().toString())
                .bookingDate(bookingModel.getBookingDate())
                .bookingStatus(BookingStatus.CREATED)
                .mobileNumber(bookingModel.getMobileNumber())
                .customerName(bookingModel.getCustomerName())
                .numberOfRoom(bookingModel.getNumberOfRoom())
                .numberOfDays(bookingModel.getNumberOfDays())
                .roomTypeId(bookingModel.getRoomTypeId())
                .build();

//        try (SubscriptionQueryResult<BookingSummary, BookingSummary> queryResult =
//                     queryGateway.subscriptionQuery(new FindBookingQuery(bookingCommand.getBookingId()),
//                             ResponseTypes.instanceOf(BookingSummary.class),
//                             ResponseTypes.instanceOf(BookingSummary.class))) {
//            commandGateway.sendAndWait(bookingCommand);
//
//            return queryResult.updates().blockFirst();
//        }
        try {
            commandGateway.sendAndWait(bookingCommand);
        }catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Booking is successfully created.");
    }
}
