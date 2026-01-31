package com.uexcel.roomservice.command.repository;

import com.uexcel.roomservice.command.entity.BookingSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingSummaryRepository extends JpaRepository<BookingSummary, String> {

}
