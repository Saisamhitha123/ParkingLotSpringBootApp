package com.example.demo.controller;

import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.model.entity.ParkingLot;
import com.example.demo.service.DisplayPanelOperationService.DisplayPanelOperationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DisplayPanelController {
    private static final Logger logger = LoggerFactory.getLogger(DisplayPanelController.class);

    @Autowired
    DisplayPanelOperationService displayPanelOperationService;

    /**
     * Adds a DisplayPanel to a specified ParkingLot.
     *
     * @param parkingLotId   The ID of the parking lot to which the display panel is to be added.
     * @param displayPanels  The display panel object received in the request body.
     * @return               The updated ParkingLot entity with the new display panel.
     * @throws ParkingLotNotFoundException if the parkingLotId is null or not found.
     */
    @PostMapping("/v1/parkingLots/{parkingLotId}/displayPanels")
    public ResponseEntity<ParkingLot> addDisplayPanel(@PathVariable Long parkingLotId, @RequestBody DisplayPanel displayPanels)throws ParkingLotNotFoundException {
        if (parkingLotId == null) {
            throw new ParkingLotNotFoundException("Parking lot ID is required");
        }

        ParkingLot parkingLotResponse = displayPanelOperationService.addDisplayPanel(parkingLotId, displayPanels);
        logger.info("Display Panel added successfully to parking lot"+parkingLotId);
        return new ResponseEntity<ParkingLot>(parkingLotResponse, HttpStatus.OK);
    }

}
