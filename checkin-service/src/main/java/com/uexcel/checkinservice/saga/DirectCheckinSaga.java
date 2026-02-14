package com.uexcel.checkinservice.saga;

import com.uexcel.checkinservice.CheckinStatus;
import com.uexcel.checkinservice.command.*;
import com.uexcel.checkinservice.query.FindCheckinByIdQuery;
import com.uexcel.checkinservice.query.controller.CheckinSummaryModel;
import com.uexcel.common.RoomStatus;
import com.uexcel.common.command.UpdateRoomForCheckinCommand;
import com.uexcel.common.command.UpdateRoomInventoryForDateForCheckinCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.axonframework.spring.stereotype.Saga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.TimeUnit;

@Saga
public class DirectCheckinSaga {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectCheckinSaga.class);
    @Autowired
    private transient CommandGateway commandGateway;
    @Autowired
    private transient QueryUpdateEmitter queryUpdateEmitter;
    private String checkinId;

    @StartSaga
    @SagaEventHandler(associationProperty = "checkinId")
    public void on(CheckinCreatedEvent event) {
        this.checkinId = event.getCheckinId();

        commandGateway.send(new UpdateRoomForCheckinCommand(event.getRoomNumber(), RoomStatus.occupied),
                (commandMessage, resultMessage) -> {
            if (resultMessage.isExceptional()) {
                commandGateway.send(new
                        RejectCheckinCommand(event.getCheckinId(),
                        resultMessage.exceptionResult().getMessage()));
            }else {
                commandGateway.send(
                        new UpdateRoomInventoryForDateForCheckinCommand(
                                event.getRoomInventoryForDateId(), event.getReservationId(),event.getRoomTypeName(),
                                event.getRoomNumber()
                        ),
                        (commandMsg, resultMsg) -> {
                            if (resultMsg.isExceptional()) {
                                commandGateway.sendAndWait(new UpdateRoomForCheckinCommand(
                                        event.getRoomNumber(), RoomStatus.available)
                                );
                                commandGateway.send(new
                                        RejectCheckinCommand(event.getCheckinId(),
                                        resultMsg.exceptionResult().getMessage()));
                            }else {
                                commandGateway.send(
                                        new UpdateCheckinStatusCommand(event.getCheckinId(),CheckinStatus.succeeded)
                                );
                            }
                        });
            }
        });
    }


    @EndSaga
    @SagaEventHandler(associationProperty = "checkinId")
    public void on(CheckinRejectedEvent  event) {
        LOGGER.info("Checkin was rejected due to reason={}", event.getReason());
        CheckinSummaryModel checkinSummaryModel = new CheckinSummaryModel();
        checkinSummaryModel.setCheckinId(checkinId);
        checkinSummaryModel.setReason(event.getReason());
        checkinSummaryModel.setCheckinStatus(CheckinStatus.failed);
        queryUpdateEmitter.emit(
                FindCheckinByIdQuery.class,
                query -> true, checkinSummaryModel);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "checkinId")
    public void on(CheckinSucceededEvent event) {
        CheckinSummaryModel checkinSummaryModel = new CheckinSummaryModel();
        checkinSummaryModel.setCheckinId(checkinId);
        checkinSummaryModel.setCheckinStatus(event.getCheckinStatus());

        queryUpdateEmitter.emit(
                FindCheckinByIdQuery.class,
                query -> true, checkinSummaryModel);
    }


    @StartSaga
    @SagaEventHandler(associationProperty = "checkinId")
    public void on(CheckinCalledEvent checkinCalledEvent){
        commandGateway.send(new UpdateRoomForCheckinCommand(checkinCalledEvent.getRoomNumber(), RoomStatus.available),
                (commandMessage,
                 resultMessage) -> {
                    if (!resultMessage.isExceptional()) {
                        LOGGER.info("Room has been successfully updated");
                        SagaLifecycle.end();
                    }
        });
    }






    private void   UpdateRoomForCheckinCommand(CheckinCreatedEvent event){
        commandGateway.send(new UpdateRoomForCheckinCommand(event.getRoomNumber(), RoomStatus.occupied),
                (commandMessage, resultMessage) -> {
                    if (resultMessage.isExceptional()) {
                        commandGateway.send(new
                                RejectCheckinCommand(event.getCheckinId(),
                                resultMessage.exceptionResult().getMessage()));
                    }
                });
    }

}
