package com.uexcel.roomservice;

import com.uexcel.common.event.ReservationCreatedEvent;
import com.uexcel.common.event.ReservationResponse;
import com.uexcel.roomservice.command.entity.Reservation;
import com.uexcel.roomservice.command.entity.RoomType;
import com.uexcel.roomservice.command.repository.ReservationRepository;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ReservationService {
    private final RoomTypeRepository roomTypeRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomTypeRepository roomTypeRepository,
                              ReservationRepository reservationRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.reservationRepository = reservationRepository;
    }

    public ReservationResponse validateReservation(ReservationCreatedEvent event) {


        if (event.getBookingDate().isBefore(LocalDate.now())) {
            return new ReservationResponse(false, "Past dates are not allowed!");
        }

        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(event, reservation);

        Reservation reservedRooms =
                reservationRepository.findByRoomTypeIdAndBookingDate(event.getRoomTypeId(), event.getBookingDate());
        RoomType roomType = roomTypeRepository.findByRoomTypeId(event.getRoomTypeId());
        if (event.getBookingDate().isAfter(LocalDate.now())) {
            if (reservedRooms == null && roomType.getQuantity() <= event.getBookedQuantity()) {
                reservationRepository.save(reservation);
                return new ReservationResponse(true, "success");
            } else {
                if (reservedRooms != null) {
                    int availableRooms = roomType.getQuantity() - reservedRooms.getAvailableRooms();

                    if (event.getBookedQuantity() <= availableRooms) {
                        reservedRooms.setAvailableRooms(reservedRooms.getAvailableRooms() + event.getBookedQuantity());
                        reservationRepository.save(reservedRooms);
                        return new ReservationResponse(true, "success");

                    } else {
                        return new ReservationResponse(false,
                                "The number of rooms is insufficient for the reservation.");

                    }
                }
            }

        } else {
            if (reservedRooms != null) {

                int availableRooms = roomType.getQuantity() - (event.getCheckinCount()
                        + reservedRooms.getAvailableRooms());

                if (availableRooms < 0) {
                    return new ReservationResponse(false,
                            "The number of rooms is insufficient for the reservation.");
                }

                if (availableRooms >= event.getBookedQuantity()) {
                    reservedRooms.setAvailableRooms(
                            reservedRooms.getAvailableRooms() + event.getBookedQuantity()
                    );
                    reservationRepository.save(reservedRooms);
                    return new ReservationResponse(true, "success");
                } else {
                    return new ReservationResponse(false,
                            "The number of rooms is insufficient for the reservation.");
                }
            } else {
                if (roomType.getQuantity() >= event.getBookedQuantity()) {
                    reservationRepository.save(reservation);
                    return new ReservationResponse(true, "success");
                }
            }

        }
        return new ReservationResponse(false, "Failed");
    }
}
