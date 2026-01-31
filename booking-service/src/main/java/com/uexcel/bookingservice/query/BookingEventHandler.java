package com.uexcel.bookingservice.query;

import com.uexcel.bookingservice.command.entity.Booking;
import com.uexcel.bookingservice.command.repository.BookingRepository;
import com.uexcel.common.event.BookingCreatedEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ProcessingGroup("booking-group")
public class BookingEventHandler {
    private final BookingRepository bookingRepository;

    public BookingEventHandler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @EventHandler
    @Transactional
    public void on(BookingCreatedEvent event){
        Booking  booking = new Booking();
        BeanUtils.copyProperties(event,booking);
        bookingRepository.save(booking);
    }
}
