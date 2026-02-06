package com.uexcel.reservationservice.saga;

import com.uexcel.reservationservice.command.*;
import com.uexcel.common.BookingStatus;
import com.uexcel.common.command.CreateRoomReserveCommand;
import com.uexcel.common.event.PublishRoomReservedEvent;
import com.uexcel.common.event.RoomReservationRejectedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Saga
public class ReservationSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReservationSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;

    private String reservationId;


    @StartSaga
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(ReservationCreatedEvent event) {
        this.reservationId = event.getReservationId();
        LOGGER.info("BookingSaga started for bookingId={}", event.getReservationId());

        CreateRoomReserveCommand command = CreateRoomReserveCommand.builder()
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
        commandGateway.send(command);
    }


    @SagaEventHandler(associationProperty = "reservationId")
    public void on(PublishRoomReservedEvent event) {
        commandGateway.send(
                new CreateConfirmReservationCommand(event.getReservationId(),event.getRoomTypeName())
        );
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(PublishReservationConfirmedEvent event) {
        LOGGER.info("Reservation approved for bookingId={} room type name {}"
                ,event.getReservationId(),event.getRoomTypeName());
    }


    @SagaEventHandler(associationProperty = "reservationId")
    public void on(RoomReservationRejectedEvent event) {
        CancelReservationCommand cancelReservationCommand =
                CancelReservationCommand.builder()
                        .reservationId(event.getReservationId())
                        .bookingStatus(BookingStatus.REJECTED)
                        .reason(event.getReason())
                        .build();

        commandGateway.send(cancelReservationCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(ReservationCanceledEvent event) {

        LOGGER.info("Booking canceled successfully for bookingId={} reason={}",
                event.getReservationId(), event.getReason());
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
