package com.uexcel.checkinservice.command;

import com.uexcel.checkinservice.CheckinStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@Aggregate
public class CheckinAggregate {
    @AggregateIdentifier
    private String checkinId;
    private String paymentId;
    private String roomNumber;
    private String customerName;
    private String mobileNumber;
    private String nextOfKinMobileNumber;
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;
    private double paid;
    private CheckinStatus checkinStatus;

    public CheckinAggregate() {}

    @CommandHandler
    public  CheckinAggregate(CreateCheckinCommand createCheckinCommand) {
        CheckinCreatedEvent checkinCreatedEvent = new CheckinCreatedEvent();
        BeanUtils.copyProperties(createCheckinCommand, checkinCreatedEvent);
        AggregateLifecycle.apply(checkinCreatedEvent);
    }

    @CommandHandler
    public void  on(RejectCheckinCommand rejectCheckinCommand) {
        CheckinRejectedEvent checkinRejectedEvent = CheckinRejectedEvent.builder()
                .checkinId(checkinId)
                .checkinStatus(CheckinStatus.failed)
                .build();
        BeanUtils.copyProperties(rejectCheckinCommand, checkinRejectedEvent);
        AggregateLifecycle.apply(checkinRejectedEvent);
    }

    @CommandHandler
    public void on(UpdateCheckinStatusCommand  UpdateCheckinStatusCommand) {
        CheckinSucceededEvent succeededEvent = new CheckinSucceededEvent();
        BeanUtils.copyProperties(UpdateCheckinStatusCommand, succeededEvent);
        AggregateLifecycle.apply(succeededEvent);
    }

    @CommandHandler
    public void on(CancelCheckinCommand  cancelCheckinCommand) {
        CheckinCalledEvent checkinCalledEvent = CheckinCalledEvent.builder()
                .checkinId(cancelCheckinCommand.getCheckinId())
                .roomNumber(cancelCheckinCommand.getRoomNumber())
                .checkinStatus(CheckinStatus.cancelled)
                .build();
        AggregateLifecycle.apply(checkinCalledEvent);
    }

    @EventSourcingHandler
    public void on(CheckinCreatedEvent checkinCreatedEvent) {
        this.checkinId = checkinCreatedEvent.getCheckinId();
        this.paymentId = checkinCreatedEvent.getPaymentId();
        this.roomNumber = checkinCreatedEvent.getRoomNumber();
        this.customerName = checkinCreatedEvent.getCustomerName();
        this.mobileNumber = checkinCreatedEvent.getMobileNumber();
        this.nextOfKinMobileNumber = checkinCreatedEvent.getNextOfKinMobileNumber();
        this.checkinAt = checkinCreatedEvent.getCheckinAt();
        this.checkoutAt = checkinCreatedEvent.getCheckoutAt();
        this.paid = checkinCreatedEvent.getPaid();

    }

    @EventSourcingHandler
    public void on(CheckinRejectedEvent checkinRejectedEvent) {
        this.checkinStatus = checkinRejectedEvent.getCheckinStatus();

    }

    @EventSourcingHandler
    public void on(CheckinSucceededEvent checkinSucceededEvent) {
        this.checkinStatus = checkinSucceededEvent.getCheckinStatus();
    }

    @EventSourcingHandler
    public void on(CheckinCalledEvent checkinCalledEvent) {
        this.checkinStatus = checkinCalledEvent.getCheckinStatus();
    }


}
