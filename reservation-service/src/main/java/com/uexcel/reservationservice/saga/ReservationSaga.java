package com.uexcel.reservationservice.saga;

import com.uexcel.reservationservice.command.*;
import com.uexcel.common.ReservationStatus;
import com.uexcel.common.command.ReserveRoomCommand;
import com.uexcel.reservationservice.query.ReservationSummary;
import com.uexcel.reservationservice.event.ReservationCanceledEvent;
import com.uexcel.reservationservice.event.ReservationConfirmedEvent;
import com.uexcel.reservationservice.event.ReservationCreatedEvent;
import com.uexcel.reservationservice.query.FindReservationQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
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
    private QueryUpdateEmitter queryUpdateEmitter;


    @StartSaga
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(ReservationCreatedEvent event) {
        this.reservationId = event.getReservationId();
        LOGGER.info("BookingSaga started for bookingId={}", event.getReservationId());

        ReserveRoomCommand command = ReserveRoomCommand.builder()
                .roomInventoryForDateId(event.getRoomInventoryForDateId())
                .roomTypeId(event.getRoomTypeId())
                .reservationId(event.getReservationId())
                .bookedQuantity(event.getBookedQuantity())
                .bookingDate(event.getBookingDate())
                .roomTypeName(event.getRoomTypeName())
                .price(event.getPrice())
                .customerName(event.getCustomerName())
                .mobileNumber(event.getMobileNumber())
                .build();

        commandGateway.send(command, (commandMessage,
                                      resultMessage) -> {
            if (resultMessage.isExceptional()) {
                LOGGER.info("Saga execution failed. ...booking cancel booking sent!");
                CancelReservationCommand cancelReservationCommand = CancelReservationCommand.builder()
                        .reservationId(event.getReservationId())
                        .reservationStatus(ReservationStatus.rejected)
                        .reason(resultMessage.exceptionResult().getMessage())
                        .bookedQuantity(event.getBookedQuantity())
                        .price(event.getPrice())
                        .bookingDate(event.getBookingDate())
                        .mobileNumber(event.getMobileNumber())
                        .customerName(event.getCustomerName())
                        .paymentStatus(event.getPaymentStatus())
                        .roomTypeName(event.getRoomTypeName())
                        .build();
                commandGateway.send(cancelReservationCommand);
            } else {
                LOGGER.info("Saga execution succeeded");
                ConfirmReservationCommand confirmReservationCommand = new ConfirmReservationCommand();
                BeanUtils.copyProperties(event, confirmReservationCommand);
                commandGateway.send(confirmReservationCommand);
            }
        });
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
}

















//@Saga
//public class BookingSaga {
//    private static final Logger LOGGER = LoggerFactory.getLogger(BookingSaga.class);
//    @Autowired
//    private transient CommandGateway commandGateway;
//    @Autowired
//    private QueryGateway queryGateway;
//    @Autowired
//    private QueryUpdateEmitter queryUpdateEmitter;
//

//    @StartSaga
//    @SagaEventHandler(associationProperty = "bookingId")
//    public void on(BookingCreatedEvent event) {
//        FindCheckinQuery query =
//                new FindCheckinQuery(event.getRoomTypeId(), event.getBookingDate());
//
//        Integer checkinCount =
//                queryGateway.query(query, ResponseTypes.instanceOf(Integer.class)).join();
//
//        CreateReservationCommand createReservationCommand = CreateReservationCommand.builder()
//                .reservationId(event.getRoomTypeId()+event.getBookingDate())
//                .roomTypeId(event.getRoomTypeId())
//                .bookedQuantity(event.getNumberOfRoom())
//                .bookingDate(event.getBookingDate())
//                .checkinCount(checkinCount)
//                .bookingId(event.getBookingId())
//                .build();
//
//        commandGateway.send(createReservationCommand,
//        (commandMessage,
//         resultMessage) -> {
//            if (resultMessage.isExceptional()) {
//                LOGGER.info("Saga execution failed. ...booking cancel booking sent!");
//                CancelBookingCommand rBC = CancelBookingCommand.builder()
//                        .bookingId(event.getBookingId())
//                        .bookingStatus(BookingStatus.REJECTED)
//                        .numberOfRoom(event.getNumberOfRoom())
//                        .bookingDate(event.getBookingDate())
//                        .numberOfDays(event.getNumberOfDays())
//                        .customerName(event.getCustomerName())
//                        .mobileNumber(event.getMobileNumber())
//                        .roomTypeId(event.getRoomTypeId())
//                        .reason(resultMessage.exceptionResult().getMessage())
//                        .build();
//                commandGateway.send(rBC);
//            }else  {
//                LOGGER.info("Saga execution succeeded");
//                SagaLifecycle.end();
//            }
//        });
//    }
//
//
//    @EndSaga
//    @SagaEventHandler(associationProperty = "bookingId")
//    public void on(BookingCanceledEvent event) {
//        LOGGER.info("Booking rejected: booking ID: {} reason: {}",event.getBookingId(),event.getReason());
//    }
//
//
//    @EndSaga
//    @SagaEventHandler(associationProperty = "bookingId")
//    public void on(ReservationRejectedEvent event) {
//        LOGGER.info("Booking rejected : booking ID: {} reason: {}",event.getBookingId(),event.getReason());
//        CancelBookingCommand cancelBookingCommand = CancelBookingCommand.builder()
//                .bookingId(event.getBookingId())
//                .reason(event.getReason())
//                .build();
//        commandGateway.send(cancelBookingCommand);
//    }



//    @SagaEventHandler(associationProperty = "reservationId")
//    public void on(ReservationCreatedEvent event) {
//
////        queryUpdateEmitter.emit(FindBookingQuery.class,
////                query->true,
////                new OrderSummary(orderApprovedEvent.getOrderId(),
////                        orderApprovedEvent.getOrderStatus(),""));
//        LOGGER.info("Booking Create successfully: reason: {}",event.getBookingId());
//        SagaLifecycle.end();
//    }


//}


//deadlineManager.schedule(
//        Duration.ofMinutes(5),
//    "my-deadline",
//            new MyDeadlinePayload()
//);
