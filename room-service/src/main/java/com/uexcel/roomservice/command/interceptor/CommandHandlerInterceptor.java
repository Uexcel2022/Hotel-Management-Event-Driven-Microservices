package com.uexcel.roomservice.command.interceptor;


import com.uexcel.common.command.CreateRoomReserveCommand;
import com.uexcel.common.event.RoomReservationRejectedEvent;
import com.uexcel.roomservice.command.entity.RoomInventoryForDate;
import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommandHandlerInterceptor
        implements MessageHandlerInterceptor<CommandMessage<?>> {

    private final EventBus eventBus;
    private final RoomInventoryForDateRepository roomInventoryForDateRepository;

    public CommandHandlerInterceptor(EventBus eventBus,
                                     RoomInventoryForDateRepository roomInventoryForDateRepository) {
        this.eventBus = eventBus;
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
    }

    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> uow,
                         @NonNull InterceptorChain chain) throws Exception {

        CommandMessage<?> cmd = uow.getMessage();
        if (cmd.getPayload() instanceof CreateRoomReserveCommand command) {
            RoomInventoryForDate roomInventoryForDate = roomInventoryForDateRepository
                    .findByRoomInventoryForDateId(command.getRoomInventoryForDateId());
            if (roomInventoryForDate == null || LocalDate.now().isAfter(command.getBookingDate())) {
                publishError(command,eventBus,"Invalid room type reservation ID or Past date!");
                return null;

            } else {
                boolean currentAvailableRooms = roomInventoryForDate.getAvailableRooms() < command.getBookedQuantity();
                if (currentAvailableRooms) {

                    publishError(command,eventBus,"Insufficient number of available rooms!");
                    return null;
                }else {
                    if(!roomInventoryForDate.getRoomTypeName().equalsIgnoreCase(command.getRoomTypeName())||
                            !roomInventoryForDate.getBookingDate().isEqual(command.getBookingDate())||
                            !roomInventoryForDate.getRoomTypeId().equals(command.getRoomTypeId())) {
                        publishError(command,eventBus,"Room type or date mismatch!");
                        return null;
                    }
                }
            }
        }
        return chain.proceed();
    }


    private static void publishError(CreateRoomReserveCommand command,
                                     EventBus eventBus, String message) {
        RoomReservationRejectedEvent roomReservationRejectedEvent = new RoomReservationRejectedEvent(
                command.getReservationId(),
                command.getRoomTypeId(),
                command.getReservationId(),
                command.getBookedQuantity(),
                command.getBookingDate(),
                message
        );
        eventBus.publish(GenericEventMessage.asEventMessage(roomReservationRejectedEvent));
    }
}

