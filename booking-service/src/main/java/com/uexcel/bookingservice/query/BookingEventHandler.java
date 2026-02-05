package com.uexcel.bookingservice.query;

import com.uexcel.bookingservice.command.BookingCreatedEvent;
import com.uexcel.bookingservice.command.BookingCanceledEvent;
import com.uexcel.bookingservice.command.entity.Booking;
import com.uexcel.bookingservice.command.repository.BookingRepository;
import com.uexcel.common.BookingStatus;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("booking-group")
public class BookingEventHandler {
    private final BookingRepository bookingRepository;

    public BookingEventHandler(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }
    @EventHandler
    public void on(BookingCreatedEvent event){
        Booking  booking = new Booking();
        BeanUtils.copyProperties(event,booking);
        bookingRepository.save(booking);
    }

    @EventHandler
    public void on(BookingCanceledEvent event){
        Booking booking =
                bookingRepository.findByBookingId(event.getBookingId());
        if(booking==null){
            throw new IllegalStateException("booking not found.");
        }
        booking.setBookingStatus(BookingStatus.REJECTED);
        bookingRepository.save(booking);
    }

}
