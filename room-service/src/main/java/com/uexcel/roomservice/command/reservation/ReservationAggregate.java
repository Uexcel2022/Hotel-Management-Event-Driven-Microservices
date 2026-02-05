package com.uexcel.roomservice.command.reservation;

import com.google.common.eventbus.EventBus;
import com.uexcel.common.error.CustomBadRequestException;
import com.uexcel.common.event.ReservationCreatedEvent;
import com.uexcel.common.event.ReservationUpdatedEvent;
import com.uexcel.roomservice.command.entity.Reservation;
import com.uexcel.roomservice.command.entity.RoomType;
import com.uexcel.roomservice.command.repository.ReservationRepository;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Aggregate
public class ReservationAggregate {
    @AggregateIdentifier
    private String reservationId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate bookingDate;

    public ReservationAggregate() {
    }

    @CommandHandler
    public ReservationAggregate(CreateReservationCommand command) {
        ReservationCreatedEvent reservationCreatedEvent = new ReservationCreatedEvent();
        BeanUtils.copyProperties(command, reservationCreatedEvent);
        AggregateLifecycle.apply(reservationCreatedEvent);
    }

    @EventSourcingHandler
    public void on(ReservationCreatedEvent event) {
        this.reservationId = event.getReservationId();
        this.roomTypeId = event.getRoomTypeId();
        this.bookingDate = event.getBookingDate();
        this.availableRooms = event.getBookedQuantity();
    }

    @EventSourcingHandler
    public void on(ReservationUpdatedEvent event) {
        this.availableRooms -= this.availableRooms;

    }


}



//@Aggregate
//public class InventoryAggregate {
//
//    @AggregateIdentifier
//    private String productId;
//
//    private int availableQuantity;
//
//    protected InventoryAggregate() {
//        // Required by Axon
//    }
//
//    @CommandHandler
//    public InventoryAggregate(CreateInventoryCommand cmd) {
//        AggregateLifecycle.apply(
//                new InventoryCreatedEvent(cmd.getProductId(), cmd.getInitialQuantity())
//        );
//    }
//
//    @CommandHandler
//    public void handle(ReserveInventoryCommand cmd) {
//
//        if (availableQuantity < cmd.getQuantity()) {
//            AggregateLifecycle.apply(
//                    new InventoryReservationFailedEvent(
//                            cmd.getOrderId(),
//                            cmd.getProductId(),
//                            "INSUFFICIENT_STOCK"
//                    )
//            );
//            return;
//        }
//
//        AggregateLifecycle.apply(
//                new InventoryReservedEvent(
//                        cmd.getOrderId(),
//                        cmd.getProductId(),
//                        cmd.getQuantity()
//                )
//        );
//    }
//}


//if (LocalDate.now().isAfter(command.getBookingDate())) {
//        throw new IllegalArgumentException("Past dates are not valid!");
//        }
//
//
//ReservationCreatedEvent reservationCreatedEvent = new ReservationCreatedEvent();
//            BeanUtils.copyProperties(command, reservationCreatedEvent);
//
//RoomType roomType = roomTypeRepository.findByRoomTypeId(command.getRoomTypeId());
//
//            if (roomType == null) {
//        throw new CustomBadRequestException(command.getBookingId(), "Room type not found");
//        }
//
//Reservation reservedRooms = reservationRepository.findByRoomTypeIdAndBookingDate(
//        command.getRoomTypeId(), command.getBookingDate()
//);
//
//            if (reservedRooms != null && command.getBookingDate().isAfter(LocalDate.now())) {
//int availableRooms = roomType.getQuantity() - reservedRooms.getBookedQuantity();
//                if (availableRooms <= 0 || availableRooms < command.getBookedQuantity()) {
//        throw new IllegalArgumentException("Insufficient number of rooms");
//                } else {
//                        reservationCreatedEvent.setBookedQuantity(command.getBookedQuantity()
//                            + reservedRooms.getBookedQuantity());
//        }
//        } else {
//        if (reservedRooms != null && command.getBookingDate().isEqual(LocalDate.now())) {
//int availableRooms = roomType.getQuantity() -
//        (reservedRooms.getBookedQuantity() + command.getCheckinCount());
//
//                    if (availableRooms <= 0 || availableRooms < command.getBookedQuantity()) {
//        throw new IllegalArgumentException("Insufficient number of rooms");
//                    } else {
//                            reservationCreatedEvent.setBookedQuantity(command.getBookedQuantity()
//                                + reservedRooms.getBookedQuantity());
//        }
//        } else {
//        if (reservedRooms == null && command.getBookingDate().isAfter(LocalDate.now())) {
//        if (roomType.getQuantity() < command.getBookedQuantity()) {
//        throw new IllegalArgumentException("Insufficient number of rooms");
//                        }
//                                } else {
//                                if (roomType.getQuantity() < (command.getBookedQuantity() + command.getCheckinCount())) {
//        throw new IllegalArgumentException("Insufficient number of rooms");
//                        }
//                                }
//                                }
//                                }

