package com.uexcel.reservationservice.query;

import com.uexcel.reservationservice.command.repository.CheckinRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class ReservationQueryHandler {
    private final CheckinRepository checkinRepository;

    public ReservationQueryHandler(CheckinRepository checkinRepository) {
        this.checkinRepository = checkinRepository;
    }

    @QueryHandler
    public Integer on(FindCheckinQuery query){
        return checkinRepository.countByRoomTypeIdAndCheckinDate(
                query.getRoomTypeId(), query.getBookingDate()
        );
    }
}
