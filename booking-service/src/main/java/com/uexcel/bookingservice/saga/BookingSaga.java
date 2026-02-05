package com.uexcel.bookingservice.saga;

import com.uexcel.bookingservice.command.BookingCreatedEvent;
import com.uexcel.bookingservice.command.BookingCanceledEvent;
import com.uexcel.bookingservice.command.CancelBookingCommand;
import com.uexcel.bookingservice.query.FindCheckinQuery;
import com.uexcel.common.BookingStatus;
import com.uexcel.common.command.CreateApproveBookingCommand;
import com.uexcel.common.event.ReservationCreatedEvent;
import com.uexcel.common.event.BookingNotApprovedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Saga
public class BookingSaga {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingSaga.class);

    @Autowired
    private transient CommandGateway commandGateway;

    @Autowired
    private transient QueryGateway queryGateway;

    // -----------------------------
    // 1️⃣ START SAGA
    // -----------------------------
    @StartSaga
    @SagaEventHandler(associationProperty = "bookingId")
    public void on(BookingCreatedEvent event) {

        LOGGER.info("BookingSaga started for bookingId={}", event.getBookingId());

        FindCheckinQuery query =
                new FindCheckinQuery(event.getRoomTypeId(), event.getBookingDate());

        Integer checkinCount =
                queryGateway.query(query, ResponseTypes.instanceOf(Integer.class)).join();

        CreateApproveBookingCommand updateCommand = CreateApproveBookingCommand.builder()
                .reservationId(event.getRoomTypeId())
                .roomTypeId(event.getRoomTypeId())
                .bookedQuantity(event.getNumberOfRoom())
                .bookingDate(event.getBookingDate())
                .checkinCount(checkinCount)
                .bookingId(event.getBookingId())
                .build();

        // ✅ Send command ONLY — no callbacks, no decisions here
        commandGateway.send(updateCommand);
    }

    // -----------------------------
    // 2️⃣ SUCCESS PATH → END SAGA
    // -----------------------------
    @SagaEventHandler(associationProperty = "reservationId")
    public void on(ReservationCreatedEvent event) {

        LOGGER.info("Reservation created successfully for bookingId={}",
                    event.getBookingId());

            SagaLifecycle.end();
    }

    // -----------------------------
    // 3️⃣ FAILURE PATH → COMPENSATE → END SAGA
    // -----------------------------
    @SagaEventHandler(associationProperty = "bookingId")
    public void on(BookingNotApprovedEvent event) {

        LOGGER.error("Reservation rejected for bookingId={} reason={}",
                event.getBookingId(), event.getReason());

        CancelBookingCommand cancelBookingCommand =
                CancelBookingCommand.builder()
                        .bookingId(event.getBookingId())
                        .bookingStatus(BookingStatus.REJECTED)
                        .reason(event.getReason())
                        .build();

        commandGateway.send(cancelBookingCommand);

        // 🔴 THIS is the key line you were missing before
        SagaLifecycle.end();
    }

    // -----------------------------
    // 4️⃣ OPTIONAL: LOG FINAL STATE
    // -----------------------------

    @SagaEventHandler(associationProperty = "bookingId")
    public void on(BookingCanceledEvent event) {

        LOGGER.info("Booking canceled successfully for bookingId={} reason={}",
                event.getBookingId(), event.getReason());

        SagaLifecycle.end();
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
