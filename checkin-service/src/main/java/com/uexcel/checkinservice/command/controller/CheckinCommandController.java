package com.uexcel.checkinservice.command.controller;
import com.uexcel.checkinservice.command.CancelCheckinCommand;
import com.uexcel.checkinservice.command.CreateCheckinCommand;
import com.uexcel.checkinservice.query.FindCheckinByIdQuery;
import com.uexcel.checkinservice.query.controller.CheckinSummaryModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/checkins")
public class CheckinCommandController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public CheckinCommandController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }


    @PostMapping("/direct")
    public ResponseEntity<CheckinSummaryModel> directCheckin(@RequestBody CheckinRequestModel checkinRequestModel ) {
        CreateCheckinCommand createCheckinCommand = CreateCheckinCommand.builder()
                .checkinId(UUID.randomUUID().toString())
                .paid(checkinRequestModel.getPaid())
                .roomInventoryForDateId(checkinRequestModel.getRoomInventoryForDateId())
                .paymentId(checkinRequestModel.getPaymentId())
                .mobileNumber(checkinRequestModel.getMobileNumber())
                .nextOfKinMobileNumber(checkinRequestModel.getNextOfKinMobileNumber())
                .customerName(checkinRequestModel.getCustomerName())
                .roomNumber(checkinRequestModel.getRoomNumber())
                .roomTypeName(checkinRequestModel.getRoomTypeName())
                .checkinAt(LocalDateTime.now())
                .build();

        try (SubscriptionQueryResult<CheckinSummaryModel, CheckinSummaryModel> queryResult =
                     queryGateway.subscriptionQuery(new FindCheckinByIdQuery(createCheckinCommand.getCheckinId()),
                             ResponseTypes.instanceOf(CheckinSummaryModel.class),
                             ResponseTypes.instanceOf(CheckinSummaryModel.class))) {

            commandGateway.sendAndWait(createCheckinCommand);

            return ResponseEntity.status(HttpStatus.OK).body(queryResult.updates().blockFirst());


        }
    }

    @PutMapping
    public ResponseEntity<CheckinSummaryModel> cancelCheckinId(
            @RequestBody CancelCheckinRequestModel cancelCheckinRequestModel) {
        CancelCheckinCommand cancelCheckinCommand  = new  CancelCheckinCommand(
                cancelCheckinRequestModel.getCheckinId(),
                cancelCheckinRequestModel.getRoomNumber()
        );


        try (SubscriptionQueryResult<CheckinSummaryModel, CheckinSummaryModel> queryResult =
                     queryGateway.subscriptionQuery(new FindCheckinByIdQuery(cancelCheckinCommand.getCheckinId()),
                             ResponseTypes.instanceOf(CheckinSummaryModel.class),
                             ResponseTypes.instanceOf(CheckinSummaryModel.class))) {

            commandGateway.sendAndWait(cancelCheckinCommand);

            return ResponseEntity.status(HttpStatus.OK).body(queryResult.updates().blockFirst());

        }
    }
}
