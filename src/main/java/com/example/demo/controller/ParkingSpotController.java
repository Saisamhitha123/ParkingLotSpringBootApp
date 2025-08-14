package com.example.demo.controller;

import com.example.demo.exception.ParkingFloorNotFoundException;
import com.example.demo.exception.ParkingLotCreationException;
import com.example.demo.exception.ParkingLotNotFoundException;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ParkingSpotController {

    private static final Logger logger = LoggerFactory.getLogger(ParkingSpotController.class);


    @Autowired
    ParkingFloorService parkingFloorService;
    /**
     * Adds a list of parking spots to a specific parking floor.
     *
     * @param parkingFloorId ID of the parking floor to which spots are to be added
     * @param parkingSpot   List of parking spots to be added
     * @return Updated ParkingFloor entity with the newly added spots
     * @throws ParkingFloorNotFoundException if the parking floor ID is null or not found
     * @throws ParkingLotNotFoundException   if the corresponding parking lot is not found
     * @throws ParkingLotCreationException   if there's a failure in updating the parking floor
     */
    @PostMapping("/v1/parkingLots/{parkingFloorId}/parkingSpots")
    public ResponseEntity<ParkingFloor> addParkingLot(@PathVariable Long parkingFloorId, @Valid @RequestBody List<ParkingSpot> parkingSpot) throws ParkingLotCreationException, ParkingFloorNotFoundException, ParkingLotNotFoundException {
        if(parkingFloorId == null) {
            throw new ParkingFloorNotFoundException("ParkingFloorId is required");
        }

        ParkingFloor parkingFloor = parkingFloorService.addParkingSpotToParkingFloor(parkingFloorId, parkingSpot);
        logger.info("Successfully added parking spots to parking floor ID: {}", parkingFloorId);

        return new ResponseEntity<>(parkingFloor, HttpStatus.OK);
    }
}
