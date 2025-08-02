package com.example.demo.controller;

import com.example.demo.exception.ParkingFloorCreationException;
import com.example.demo.exception.ParkingFloorNotFoundException;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.model.entity.ParkingFloor;
import com.example.demo.model.entity.ParkingLot;
import com.example.demo.model.entity.ParkingSpot;
import com.example.demo.service.ParkingFloorService.ParkingFloorService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ParkingFloorController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingFloorController.class);

    @Autowired
    ParkingFloorService parkingFloorService;
    /**
     * Adds a list of parking floors to a specific parking lot.
     *
     * @param parkingLotId    ID of the parking lot
     * @param parkingFloors   List of parking floors to be added
     * @return Updated ParkingLot object
     * @throws ParkingFloorCreationException if creation fails
     * @throws ParkingLotNotFoundException if parking lot ID is invalid
     */


    @PostMapping("/v1/parkingLots/{parkingLotId}/parkingFloors")
    public ResponseEntity<ParkingLot> addParkingFloors(@Valid @PathVariable Long parkingLotId,@Valid @RequestBody List<ParkingFloor> parkingFloors) throws ParkingFloorCreationException, ParkingLotNotFoundException {
        if (parkingLotId == null) {
            throw new ParkingLotNotFoundException("Parking lot ID is required");
        }
        ParkingLot parkingLotResponse = parkingFloorService.addParkingFloor(parkingLotId, parkingFloors);
        logger.info("Successfully added parking floors to parking lot ID: {}", parkingLotId);

        return new ResponseEntity<ParkingLot>(parkingLotResponse, HttpStatus.OK);
    }

    /**
     * Fetches all parking floors for a given parking lot.
     *
     * @param parkingLotId ID of the parking lot
     * @return List of ParkingFloor objects
     * @throws ParkingLotNotFoundException if parking lot ID is not found
     */
    @GetMapping("/v1/parkingLots/{parkingLotId}")
    public ResponseEntity<List<ParkingFloor>> fetchParkingFloors(@PathVariable Long parkingLotId) throws ParkingLotNotFoundException
    {
        if (parkingLotId == null) {
            throw new ParkingLotNotFoundException("Parking lot ID is required");
        }

        List<ParkingFloor> parkingFloors=parkingFloorService.fetchParkingFloors(parkingLotId);
        logger.info("Found {} parking floors for parking lot ID: {}", parkingFloors.size(), parkingLotId);

        return new ResponseEntity<>(parkingFloors,HttpStatus.OK);
    }


    /**
     * Adds a DisplayPanel to a specific ParkingFloor.
     *
     * @param parkingFloorId ID of the parking floor
     * @param displayPanel   DisplayPanel to be added
     * @return Updated ParkingFloor object
     * @throws ParkingFloorNotFoundException if parking floor ID is not found
     */
    @PostMapping("/v1/parkingLots/parkingFloors/{parkingFloorId}/displayPanels")
    public ResponseEntity<ParkingFloor> addDisplayPanels(@PathVariable Long parkingFloorId,@RequestBody DisplayPanel displayPanel)throws ParkingFloorNotFoundException
    {
        if (parkingFloorId == null) {
            throw new ParkingFloorNotFoundException("Parking Floor ID is required");
        }


       ParkingFloor parkingFloor=parkingFloorService.addDisplayPanelToParkingFloor(parkingFloorId,displayPanel);
        logger.info("Display panel added successfully to parking floor ID: {}", parkingFloorId);

        return new ResponseEntity<>(parkingFloor,HttpStatus.OK);
    }

}
