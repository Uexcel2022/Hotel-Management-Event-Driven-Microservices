package com.uexcel.reservationservice.command.controller;

import com.uexcel.common.PaymentStatus;
import com.uexcel.reservationservice.command.CreateReservationCommand;
import com.uexcel.common.ReservationStatus;
import com.uexcel.reservationservice.command.PaymentUpdateCommand;
import com.uexcel.reservationservice.query.FindReservationQuery;
import com.uexcel.reservationservice.query.ReservationSummary;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ReservationSummary> createReservation(
            @RequestBody CreateReservationModel createReservationModel) {
        CreateReservationCommand reservationCommand = CreateReservationCommand.builder()
                .reservationId(UUID.randomUUID().toString())
                .roomInventoryForDateId(createReservationModel
                        .getRoomInventoryForDateId())
                .bookedDate(createReservationModel.getBookedDate())
                .reservationStatus(ReservationStatus.created)
                .mobileNumber(createReservationModel.getMobileNumber())
                .customerName(createReservationModel.getCustomerName())
                .roomTypeId(createReservationModel.getRoomTypeId())
                .roomTypeName(createReservationModel.getRoomTypeName())
                .price(createReservationModel.getPrice())
                .total(createReservationModel.getPrice())
                .paymentStatus(PaymentStatus.pending)
                .build();

        try (SubscriptionQueryResult<ReservationSummary, ReservationSummary> queryResult =
                     queryGateway.subscriptionQuery(new FindReservationQuery(reservationCommand.getReservationId()),
                             ResponseTypes.instanceOf(ReservationSummary.class),
                             ResponseTypes.instanceOf(ReservationSummary.class))) {
            commandGateway.sendAndWait(reservationCommand);

            return ResponseEntity.status(HttpStatus.CREATED).body(queryResult.updates().blockFirst());
        }
    }
    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationSummary> updateReservation(@PathVariable String reservationId) {

        PaymentUpdateCommand paymentUpdateCommand = PaymentUpdateCommand.builder()
                .reservationId(reservationId)
                .paymentStatus(PaymentStatus.paid).build();

        try (SubscriptionQueryResult<ReservationSummary, ReservationSummary> queryResult =
                     queryGateway.subscriptionQuery(new FindReservationQuery(paymentUpdateCommand.getReservationId()),
                             ResponseTypes.instanceOf(ReservationSummary.class),
                             ResponseTypes.instanceOf(ReservationSummary.class))) {
            commandGateway.sendAndWait(paymentUpdateCommand);

            return ResponseEntity.status(HttpStatus.OK).body(queryResult.updates().blockFirst());

        }
    }
}
