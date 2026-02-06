package com.uexcel.roomservice.command.inventory;

import com.uexcel.common.BookingStatus;
import com.uexcel.common.command.CreateRoomReserveCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;


import java.time.LocalDate;

@Aggregate
public class RoomInventoryForDateAggregate {
    @AggregateIdentifier
    private String roomInventoryForDateId;
    private String roomTypeId;
    private int availableRooms;
    private LocalDate bookingDate;
    private String roomTypeName;
    private double price;


    public RoomInventoryForDateAggregate() {
    }

    @CommandHandler
    public RoomInventoryForDateAggregate(CreateRoomInventoryForDateCommand command) {
        RoomInventoryForDateCreatedEvent roomInventoryForDateCreatedEvent = new RoomInventoryForDateCreatedEvent();
        BeanUtils.copyProperties(command, roomInventoryForDateCreatedEvent);
        AggregateLifecycle.apply(roomInventoryForDateCreatedEvent);
    }

    @CommandHandler
    public void on(CreateRoomReserveCommand command) {
        RoomInventoryForDateReservedEvent roomInventoryForDateReservedEvent = new RoomInventoryForDateReservedEvent();
        BeanUtils.copyProperties(command, roomInventoryForDateReservedEvent);
        roomInventoryForDateReservedEvent.setBookingStatus(BookingStatus.APPROVED);
        AggregateLifecycle.apply(roomInventoryForDateReservedEvent);
    }

    @EventSourcingHandler
    public void on(RoomInventoryForDateCreatedEvent event) {
        this.roomInventoryForDateId = event.getRoomInventoryForDateId();
        this.roomTypeId = event.getRoomTypeId();
        this.bookingDate = event.getBookingDate();
        this.availableRooms = event.getAvailableRooms();
        this.roomTypeName = event.getRoomTypeName();
    }

    @EventSourcingHandler
    public void on(RoomInventoryForDateReservedEvent event) {
        this.availableRooms -= event.getBookedQuantity();

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

