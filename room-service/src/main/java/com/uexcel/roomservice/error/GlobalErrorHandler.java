package com.uexcel.roomservice.error;

import com.uexcel.common.error.ErrorResponse;
import org.axonframework.commandhandling.CommandExecutionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
public class GlobalErrorHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse>
    handleCommandExecutionException(IllegalArgumentException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                timestamp(),HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toLowerCase()
                ,webRequest.getDescription(false).split("=")[1]
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommandExecutionException.class)
    public ResponseEntity<ErrorResponse>
    handleCommandExecutionException(CommandExecutionException exception, WebRequest webRequest) {
        ErrorResponse errorResponse = new ErrorResponse(
                timestamp(),HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase().toLowerCase()
                ,webRequest.getDescription(false).split("=")[1]
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }




    private String timestamp() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssz");
        return sdf.format(new Date());
    }
}
