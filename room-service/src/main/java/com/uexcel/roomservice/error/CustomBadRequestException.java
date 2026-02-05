//package com.uexcel.roomservice.error;
//
//import lombok.Getter;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ResponseStatus;
//
//@Getter
//@ResponseStatus(HttpStatus.BAD_REQUEST)
//public class CustomBadRequestException extends RuntimeException{
//    private final String bookingId;
//    public CustomBadRequestException(String bookingId, String message) {
//        super(message);
//        this.bookingId = bookingId;
//    }
//}