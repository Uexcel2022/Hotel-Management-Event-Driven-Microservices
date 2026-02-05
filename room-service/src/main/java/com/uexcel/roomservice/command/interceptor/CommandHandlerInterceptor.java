package com.uexcel.roomservice.command.interceptor;


import com.uexcel.common.command.CreateApproveBookingCommand;
import com.uexcel.common.event.BookingNotApprovedEvent;
import com.uexcel.roomservice.command.entity.Reservation;
import com.uexcel.roomservice.command.repository.ReservationRepository;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;
import org.axonframework.messaging.InterceptorChain;
import org.axonframework.messaging.MessageHandlerInterceptor;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class CommandHandlerInterceptor
        implements MessageHandlerInterceptor<CommandMessage<?>> {

    private final EventBus eventBus;
    private final ReservationRepository reservationRepository;

    public CommandHandlerInterceptor(EventBus eventBus, ReservationRepository reservationRepository) {
        this.eventBus = eventBus;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Object handle(UnitOfWork<? extends CommandMessage<?>> uow,
                         @NonNull InterceptorChain chain) throws Exception {

        CommandMessage<?> cmd = uow.getMessage();
        if (cmd.getPayload() instanceof CreateApproveBookingCommand command) {
            Reservation reservedRooms = reservationRepository
                    .findByRoomTypeIdAndBookingDate(command.getRoomTypeId(), command.getBookingDate());
            if (reservedRooms == null || LocalDate.now().isAfter(command.getBookingDate())) {
                BookingNotApprovedEvent rejectedEvent = new BookingNotApprovedEvent(
                        command.getReservationId(),
                        command.getRoomTypeId(),
                        command.getBookingId(),
                        command.getBookedQuantity(),
                        command.getBookingDate(),
                        command.getCheckinCount(),
                        "Invalid room type ID or Past date!"
                );
                eventBus.publish(
                        GenericEventMessage.asEventMessage(rejectedEvent));
                return null;

            } else {
                boolean currentAvailableRooms = reservedRooms.getAvailableRooms() < command.getBookedQuantity();
                if (currentAvailableRooms) {
                    BookingNotApprovedEvent rejectedEvent = new BookingNotApprovedEvent(
                            command.getReservationId(),
                            command.getRoomTypeId(),
                            command.getBookingId(),
                            command.getBookedQuantity(),
                            command.getBookingDate(),
                            command.getCheckinCount(),
                            "Insufficient number of available rooms!"
                    );
                    eventBus.publish(GenericEventMessage.asEventMessage(rejectedEvent));
                    return null; // or throw, see below
                }else {
                    CommandMessage<?> original = uow.getMessage();

                    CreateApproveBookingCommand oldPayload = (CreateApproveBookingCommand) original.getPayload();
                    CreateApproveBookingCommand newPayload = CreateApproveBookingCommand.builder()
                            .reservationId(reservedRooms.getReservationId())
                            .roomTypeId(oldPayload.getRoomTypeId())
                            .bookingDate(oldPayload.getBookingDate())
                            .checkinCount(oldPayload.getCheckinCount())
                            .bookedQuantity(oldPayload.getBookedQuantity())
                            .build();

                    CommandMessage<?> modifiedMessage =
                            original.andMetaData(Map.of("payload", newPayload));

                    uow.transformMessage(m -> modifiedMessage);
                }
            }
        }



        return chain.proceed();

    }
}

