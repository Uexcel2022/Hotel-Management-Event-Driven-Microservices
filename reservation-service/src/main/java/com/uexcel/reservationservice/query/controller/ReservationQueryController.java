package com.uexcel.reservationservice.query.controller;

import com.uexcel.reservationservice.query.FindReservationQuery;
import com.uexcel.reservationservice.query.ReservationSummary;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/reservations",produces = MediaType.APPLICATION_JSON_VALUE)
public class ReservationQueryController {
    private final QueryGateway queryGateway;

    public ReservationQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @RequestMapping("/{reservationId}")
    public ResponseEntity<ReservationSummary> fetchReservation(@PathVariable String reservationId) {
        return ResponseEntity.ok(queryGateway.query(new FindReservationQuery(reservationId),
                ReservationSummary.class).join());
    }

}
