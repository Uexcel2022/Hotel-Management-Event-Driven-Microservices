package com.uexcel.reservationservice.saga;

import com.uexcel.common.PaymentStatus;
import com.uexcel.common.query.FindPaymentDetailsQuery;
import com.uexcel.common.query.PaymentDetailsModel;
import com.uexcel.reservationservice.command.*;
import com.uexcel.common.ReservationStatus;
import com.uexcel.common.command.ReserveRoomCommand;
import com.uexcel.reservationservice.query.ReservationSummary;
import com.uexcel.reservationservice.event.ReservationCanceledEvent;
import com.uexcel.reservationservice.event.ReservationConfirmedEvent;
import com.uexcel.reservationservice.event.ReservationCreatedEvent;
import com.uexcel.reservationservice.query.FindReservationQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;


@Saga
public class ReservationSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;

    private String reservationId;

    @Autowired
    private transient QueryGateway queryGateway;

    @Autowired
    private QueryUpdateEmitter queryUpdateEmitter;


    @StartSaga
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(ReservationCreatedEvent event) {
        this.reservationId = event.getReservationId();

        LOGGER.info("BookingSaga started for bookingId={}", event.getReservationId());

        PaymentDetailsModel paymentDetailsModel =
                queryGateway.query(new FindPaymentDetailsQuery(event.getMobileNumber()),
                        ResponseTypes.instanceOf(PaymentDetailsModel.class)).join();

        LOGGER.info("Fetched payment details {}", paymentDetailsModel);

        if (paymentDetailsModel == null) {
            commandGateway.send(cancelReservationCommand(event, "Payment failed."));
        }
        else {

            LOGGER.info("Payment successful.");

           LOGGER.info("Sending room reservation command!");

            ReserveRoomCommand command = ReserveRoomCommand.builder()
                    .roomInventoryForDateId(event.getRoomInventoryForDateId())
                    .roomTypeId(event.getRoomTypeId())
                    .reservationId(event.getReservationId())
                    .bookedDate(event.getBookedDate())
                    .roomTypeName(event.getRoomTypeName())
                    .price(event.getPrice())
                    .total(event.getTotal())
                    .customerName(event.getCustomerName())
                    .mobileNumber(event.getMobileNumber())
                    .build();

            commandGateway.send(command, (commandMessage,
                                          resultMessage) -> {
                if (resultMessage.isExceptional()) {
                    LOGGER.info("Saga execution failed. ...reservation canceled");
                    commandGateway.send(
                            cancelReservationCommand(event,
                                    resultMessage.exceptionResult().getMessage())
                    );
                } else {

                    ConfirmReservationCommand confirmReservationCommand = new ConfirmReservationCommand();
                    BeanUtils.copyProperties(event, confirmReservationCommand);
                    confirmReservationCommand.setPaymentStatus(PaymentStatus.paid);
                    commandGateway.send(confirmReservationCommand);
                }
            });
        }
    }


    @EndSaga
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(ReservationConfirmedEvent event) {
        ReservationSummary reservationSummary = new ReservationSummary();
        BeanUtils.copyProperties(event, reservationSummary);
        LOGGER.info("Reservation approved for reservationId={} room type name {}"
                ,event.getReservationId(),event.getRoomTypeName());
        queryUpdateEmitter.emit(
                FindReservationQuery.class,
                q ->
                        q.getReservationId().equals(event.getReservationId()),
                reservationSummary
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(ReservationCanceledEvent event) {
        LOGGER.info("Booking canceled successfully for bookingId={} reason={}",
                event.getReservationId(), event.getReason());
        ReservationSummary reservationSummary = new ReservationSummary();
        BeanUtils.copyProperties(event, reservationSummary);
        queryUpdateEmitter.emit(
                FindReservationQuery.class,
                q ->
                        q.getReservationId().equals(event.getReservationId()),
                reservationSummary
        );
    }

    private CancelReservationCommand cancelReservationCommand
            (ReservationCreatedEvent event,String reason){

        return CancelReservationCommand.builder()
                .reservationId(event.getReservationId())
                .reservationStatus(ReservationStatus.rejected)
                .reason(reason)
                .price(event.getPrice())
                .bookedDate(event.getBookedDate())
                .mobileNumber(event.getMobileNumber())
                .customerName(event.getCustomerName())
                .paymentStatus(event.getPaymentStatus())
                .roomTypeName(event.getRoomTypeName())
                .total(event.getTotal())
                .build();
    }
}



