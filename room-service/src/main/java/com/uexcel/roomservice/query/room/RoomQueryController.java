package com.uexcel.roomservice.query.room;

import com.uexcel.common.RoomStatus;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/rooms")
public class RoomQueryController {
    private final QueryGateway queryGateway;

    public RoomQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/{roomTyeId}/{roomStatus}")
    public ResponseEntity<List<RoomModel>>
    getRoomTypeAndStaus(@PathVariable String roomTyeId, @PathVariable RoomStatus roomStatus) {
        return ResponseEntity.ok(queryGateway.query(new FindRoomByRoomTypeIdAndRoomStatus(roomTyeId,roomStatus),
                ResponseTypes.multipleInstancesOf(RoomModel.class)).join());
    }

    @GetMapping("/{roomTyeId}")
    public ResponseEntity<List<RoomModel>>
    getAllRoomByType(@PathVariable String roomTyeId) {
        return ResponseEntity.ok(queryGateway.query(new FindRoomByTypeIdQuery(roomTyeId),
                ResponseTypes.multipleInstancesOf(RoomModel.class)).join());
    }

    @GetMapping("/{roomNumber}/roomNumber")
    public ResponseEntity<RoomModel> getAllRoomByNumber(@PathVariable String roomNumber) {
        return ResponseEntity.ok(queryGateway.query(new FindRoomByNumberQuery(roomNumber),
                ResponseTypes.instanceOf(RoomModel.class)).join());
    }

}
