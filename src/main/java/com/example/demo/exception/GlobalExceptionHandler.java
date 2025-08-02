package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ParkingLotCreationException.class)
    public ResponseEntity<Map<String, Object>> handleParkingLotCreationException(ParkingLotCreationException ex) {
        return buildErrorResponse("Failed to create Parking Lot: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(ParkingFloorCreationException.class)
    public ResponseEntity<Map<String, Object>> handleParkingFloorCreationException(ParkingFloorCreationException ex) {
        return buildErrorResponse("Failed to create Parking Floor: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ParkingLotNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleParkingLotNotFoundException(ParkingLotNotFoundException ex) {
        return buildErrorResponse( ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(ParkingFloorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleParkingFloorNotFoundException(ParkingFloorNotFoundException ex) {
        return buildErrorResponse( ex.getMessage(), HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<Map<String, Object>> buildErrorResponse(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now());
        error.put("status", status.value());
        error.put("error", status.getReasonPhrase());
        error.put("message", message);
        return new ResponseEntity<>(error, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleInvalidEnum(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Invalid enum value for status");
    }
}
