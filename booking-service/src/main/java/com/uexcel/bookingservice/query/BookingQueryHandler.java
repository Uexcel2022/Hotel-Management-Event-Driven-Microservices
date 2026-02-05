package com.uexcel.bookingservice.query;

import com.uexcel.bookingservice.command.repository.CheckinRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class BookingQueryHandler {
    private final CheckinRepository checkinRepository;

    public BookingQueryHandler(CheckinRepository checkinRepository) {
        this.checkinRepository = checkinRepository;
    }

    @QueryHandler
    public Integer on(FindCheckinQuery query){
        return checkinRepository.countByRoomTypeIdAndCheckinDate(
                query.getRoomTypeId(), query.getBookingDate()
        );
    }
}
