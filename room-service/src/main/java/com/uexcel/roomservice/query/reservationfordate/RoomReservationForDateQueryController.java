package com.uexcel.roomservice.query.reservationfordate;

import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/rooms/reservation-for-date",produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomReservationForDateQueryController {
    private final QueryGateway queryGateway;

    public RoomReservationForDateQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public ResponseEntity<List<RoomInventoryForDateModel>> getAllRoomTypeInventoryForDate(){
       return ResponseEntity.ok(queryGateway.query(new FindAllRoomTypeInventoryForDate(),
               ResponseTypes.multipleInstancesOf(RoomInventoryForDateModel.class)).join());
    }

    @GetMapping("/{reservationForDate}/{date}")
    public ResponseEntity<List<RoomInventoryForDateModel>>
    getRoomTypeInventoryForDate(@PathVariable String reservationForDate){
        return ResponseEntity.ok(queryGateway.query(new FindReservationForDateByRoomTypeId(reservationForDate),
                ResponseTypes.multipleInstancesOf(RoomInventoryForDateModel.class)).join());
    }
}
