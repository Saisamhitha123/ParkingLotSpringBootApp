package com.example.demo.controller;

import com.example.demo.exception.ParkingLotCreationException;
import com.example.demo.model.entity.*;
import com.example.demo.service.ParkingLotService.ParkingLotService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingLotController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingLotController.class);


    @Autowired
    ParkingLotService parkingLotService;

    /**
     * Adds a new parking lot to the system.
     *
     * @param parkingLot The ParkingLot object containing details of the lot to be created
     * @return The created ParkingLot object with additional information like generated ID
     * @throws ParkingLotCreationException if the creation fails due to validation or persistence errors
     */
    @PostMapping("/v1/parkingLots")
    public ResponseEntity<ParkingLot> addParkingLot(@Valid @RequestBody ParkingLot parkingLot) throws ParkingLotCreationException {
        ParkingLot parkingLotResponse = parkingLotService.addParkingLot(parkingLot);

        logger.info("Successfully created parking lot with ID: {}", parkingLotResponse.getParkingLotId());

        return new ResponseEntity<>(parkingLotResponse, HttpStatus.OK);
    }
}
