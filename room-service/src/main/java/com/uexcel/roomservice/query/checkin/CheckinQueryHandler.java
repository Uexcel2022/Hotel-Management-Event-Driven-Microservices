package com.uexcel.roomservice.query.checkin;

import com.uexcel.common.RoomStatus;
import com.uexcel.common.query.FindRoomInfoQuery;
import com.uexcel.common.query.QueryResultSummaryForCheckin;
import com.uexcel.common.query.RoomModelForCheckinQuery;
import com.uexcel.roomservice.command.repository.RoomInventoryForDateRepository;
import com.uexcel.roomservice.command.repository.RoomRepository;
import com.uexcel.roomservice.command.repository.RoomTypeRepository;
import com.uexcel.roomservice.entity.Room;
import com.uexcel.roomservice.entity.RoomInventoryForDate;
import com.uexcel.roomservice.entity.RoomType;
import jakarta.transaction.Transactional;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CheckinQueryHandler {
    private final RoomRepository roomRepository;
    private final RoomInventoryForDateRepository roomInventoryForDateRepository;
    private final RoomTypeRepository roomTypeRepository;

    public CheckinQueryHandler(RoomRepository roomRepository,
                               RoomInventoryForDateRepository roomInventoryForDateRepository,
                               RoomTypeRepository roomTypeRepository) {
        this.roomRepository = roomRepository;
        this.roomInventoryForDateRepository = roomInventoryForDateRepository;
        this.roomTypeRepository = roomTypeRepository;
    }

    @QueryHandler
    @Transactional
    public QueryResultSummaryForCheckin handle(FindRoomInfoQuery query) {

        RoomInventoryForDate roomInventoryForDate = roomInventoryForDateRepository
                .findByRoomTypeNameAndAvailabilityDate(query.getRoomTypeName(), LocalDate.now());

        if(roomInventoryForDate == null) {
            return QueryResultSummaryForCheckin.builder()
                    .message("Information could not be found.")
                    .build();
        }

        List<Room> rooms = roomRepository
                .findByRoomTypeIdAndRoomStatus(roomInventoryForDate.getRoomTypeId(), RoomStatus.available);

        if(rooms.isEmpty()) {
            return QueryResultSummaryForCheckin.builder()
                    .message("Information could not be found.")
                    .build();
        }

        RoomType roomType = roomTypeRepository.findByRoomTypeId(roomInventoryForDate.getRoomTypeId());

        if(roomType==null) {
            return QueryResultSummaryForCheckin.builder()
                    .message("Information could not be found.")
                    .build();
        }

        if(roomInventoryForDate.getAvailableRooms() > 0) {

            return QueryResultSummaryForCheckin.builder()
                    .message("Unreserved rooms for this date: %s"
                            .formatted(roomInventoryForDate.getAvailableRooms()))
                    .availableRoomsNumberForCheckin(rooms.stream()
                            .map(room -> RoomModelForCheckinQuery.builder().roomNumber(room.getRoomNumber())
                                    .roomInventoryForDateId(roomInventoryForDate.getRoomInventoryForDateId())
                                    .roomTypeName(roomInventoryForDate.getRoomTypeName())
                                    .roomTypeId(roomInventoryForDate.getRoomTypeId())
                                    .price(roomType.getPrice())
                                    .build()).collect(Collectors.toList()))
                    .availableRoomsForCheckin(roomInventoryForDate.getAvailableRooms())
                    .build();
        }
        return QueryResultSummaryForCheckin.builder()
                .message("All the rooms are reserved for this date.")
                .build();
    }
}
