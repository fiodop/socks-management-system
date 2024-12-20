package com.socksmanagementsystem.common.exceptions;

import com.socksmanagementsystem.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(SockNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleSockNotFoundException(SockNotFoundException e) {

        logger.error("threw exception: " + e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .error(e.getClass().getName().toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientSockQuantityException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientSocksQuantityException(Exception e) {
        logger.error("threw exception: " + e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .error(e.getClass().getName().toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalOperatorException.class)
    public ResponseEntity<ErrorResponse> handleIllegalOperatorException(Exception e) {
        logger.error("threw exception: " + e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .error(e.getClass().getName().toString())
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
