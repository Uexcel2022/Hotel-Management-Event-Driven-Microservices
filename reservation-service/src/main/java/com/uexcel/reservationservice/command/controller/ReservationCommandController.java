package com.uexcel.reservationservice.command.controller;

import com.uexcel.reservationservice.command.CreateReservationCommand;
import com.uexcel.common.BookingStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/reservations",produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationCommandController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public ReservationCommandController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping
    public ResponseEntity<String> createBooking(@RequestBody CreateReservationModel createReservationModel) {
        CreateReservationCommand bookingCommand = CreateReservationCommand.builder()
                .reservationId(UUID.randomUUID().toString())
                .roomInventoryForDateId(createReservationModel
                        .getRoomInventoryForDateId())
                .bookingDate(createReservationModel.getBookingDate())
                .bookingStatus(BookingStatus.CREATED)
                .mobileNumber(createReservationModel.getMobileNumber())
                .customerName(createReservationModel.getCustomerName())
                .bookedQuantity(createReservationModel.getBookedQuantity())
                .roomTypeId(createReservationModel.getRoomTypeId())
                .roomTypeName(createReservationModel.getRoomTypeName())
                .price(createReservationModel.getPrice())
                .build();

//        try (SubscriptionQueryResult<ReservationSummary, ReservationSummary> queryResult =
//                     queryGateway.subscriptionQuery(new FindReservationQuery(""),
//                             ResponseTypes.instanceOf(ReservationSummary.class),
//                             ResponseTypes.instanceOf(ReservationSummary.class))) {
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
