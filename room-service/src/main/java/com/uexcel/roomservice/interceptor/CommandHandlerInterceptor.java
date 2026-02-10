package com.uexcel.roomservice.interceptor;


import com.uexcel.common.command.ReserveRoomCommand;
import com.uexcel.roomservice.entity.RoomInventoryForDate;
import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CommandHandlerInterceptor
        implements MessageHandlerInterceptor<CommandMessage<?>> {

    private final RoomInventoryForDateRepository roomInventoryForDateRepository;

    public CommandHandlerInterceptor(RoomInventoryForDateRepository roomInventoryForDateRepository) {
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
    }

    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> uow,
                         @NonNull InterceptorChain chain) throws Exception {

        CommandMessage<?> cmd = uow.getMessage();
        if (cmd.getPayload() instanceof ReserveRoomCommand command) {

            RoomInventoryForDate roomInventoryForDate = roomInventoryForDateRepository
                    .findByRoomInventoryForDateId(command.getRoomInventoryForDateId());

            if (roomInventoryForDate == null || LocalDate.now().isAfter(command.getBookedDate())) {
                throw new CommandExecutionException("Invalid room type reservation for date ID or Past date!", null);
            }

            if (roomInventoryForDate.getAvailableRooms() < 1) {
                throw new CommandExecutionException("The room type is currently unavailable!", null);
            }

            if (!roomInventoryForDate.getRoomTypeName().equalsIgnoreCase(command.getRoomTypeName()) ||
                    !roomInventoryForDate.getAvailabilityDate().isEqual(command.getBookedDate()) ||
                    !roomInventoryForDate.getRoomTypeId().equals(command.getRoomTypeId())) {
                throw new CommandExecutionException("Room type or date mismatch!", null);
            }
        }

        return chain.proceed();
    }

}

