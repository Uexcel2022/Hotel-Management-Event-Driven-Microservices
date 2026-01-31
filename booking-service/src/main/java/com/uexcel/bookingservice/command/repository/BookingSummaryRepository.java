package com.uexcel.bookingservice.command.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
@Repository
public interface BookingSummaryRepository extends JpaRepository<BookingSummary,String> {
    BookingSummary
    findBookingSummaryByRoomTypeAndDate(String roomType, LocalDate date);
}
