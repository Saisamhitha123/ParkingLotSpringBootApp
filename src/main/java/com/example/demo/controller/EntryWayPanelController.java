package com.example.demo.controller;

import com.example.demo.exception.EntryPanelAlreadyInUseException;
import com.example.demo.exception.EntryWayPanelNotFoundException;
import com.example.demo.exception.ParkingFloorNotFoundException;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.entity.*;
import com.example.demo.service.EntryWayPanelService.EntryWayPanelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
public class EntryWayPanelController {

    private static final Logger logger = LoggerFactory.getLogger(EntryWayPanelController.class);

    @Autowired
    EntryWayPanelService entryWayPanelService;

    /**
     * Adds a list of EntryWayPanels to the specified ParkingLot.
     *
     * @param parkingLotId     The ID of the parking lot to which the entry way panels will be added.
     * @param entryWayPanels   The list of EntryWayPanel objects received in the request body.
     * @return                 ResponseEntity containing the updated ParkingLot and HTTP status.
     * @throws ParkingLotNotFoundException if the parkingLotId is null or not found.
     */

    @PostMapping("/v1/parkingLots/{parkingLotId}/entryWayPanels")
    public ResponseEntity<ParkingLot> addEntryWayPanels(@PathVariable Long parkingLotId, @RequestBody List<EntryWayPanel> entryWayPanels) throws ParkingLotNotFoundException {
        if (parkingLotId == null) {
            throw new ParkingLotNotFoundException("Parking lot ID is required");
        }
        ParkingLot parkingLotResponse = entryWayPanelService.addEntryWayPanels(parkingLotId, entryWayPanels);
        logger.info("Entry Way Panels added successfully to parking lot ID: {}", parkingLotId);

        return new ResponseEntity<ParkingLot>(parkingLotResponse, HttpStatus.OK);
    }


    /**
     * Fetches the list of EntryWayPanels for a given parking lot ID.
     *
     * @param parkingLotId the ID of the parking lot.
     * @return a list of EntryWayPanel objects.
     * @throws ParkingLotNotFoundException if the parking lot ID is null or not found.
     * @throws EntryWayPanelNotFoundException if no entry way panels are found for the parking lot.
     */

    @GetMapping("/v1/parkingLots/{parkingLotId}/entryWayPanels")
    public ResponseEntity<List<EntryWayPanel>> fetchEntryWayPanels(@PathVariable Long parkingLotId) throws ParkingLotNotFoundException, EntryWayPanelNotFoundException {
        if (parkingLotId == null) {
            throw new ParkingLotNotFoundException("Parking lot ID is required");
        }

        List<EntryWayPanel> entryWayPanels=entryWayPanelService.getAvailablePanels(parkingLotId);

        logger.info("Successfully retrieved {} entry way panels for parking lot ID: {}", entryWayPanels.size(), parkingLotId);

        return new ResponseEntity<>(entryWayPanels,HttpStatus.OK);
    }

    /**
     * Parks a vehicle using a specified EntryWayPanel.
     *
     * @param entryWayPanelId the ID of the entry way panel.
     * @param vehicle the vehicle to be parked.
     * @return the updated DisplayPanel showing the result.
     * @throws EntryWayPanelNotFoundException if the entry way panel ID is null or not found.
     * @throws EntryPanelAlreadyInUseException if the entry panel is already processing another vehicle.
     */

    @PostMapping("/v1/parkingLots/{entryWayPanelId}/parkVehicles")
    public ResponseEntity<DisplayPanel> parkVehicle(@PathVariable Long entryWayPanelId,@RequestBody Vehicle vehicle) throws EntryWayPanelNotFoundException, EntryPanelAlreadyInUseException {
        if (entryWayPanelId == null) {
            throw new EntryWayPanelNotFoundException("EntryWay Panel Id ID is required");
        }
        DisplayPanel displayPanel=entryWayPanelService.parkVehicle(entryWayPanelId,vehicle);

        logger.info("Vehicle {} parked successfully via entry way panel ID: {}", vehicle.getVehicleNumber(), entryWayPanelId);

        return new ResponseEntity<>(displayPanel,HttpStatus.OK);
    }


    /**
     * Adds a DisplayPanel to a specified EntryWayPanel.
     *
     * @param entryWayPanelId the ID of the entry way panel.
     * @param displayPanel the display panel to be added.
     * @return the updated EntryWayPanel.
     * @throws EntryWayPanelNotFoundException if the entry way panel ID is null or not found.
     */
    @PostMapping("/v1/parkingLots/{entryWayPanelId}/entryDisplayPanels")
    public ResponseEntity<EntryWayPanel> addDisplayPanelstoEntryWayPanels(@PathVariable Long entryWayPanelId,@RequestBody DisplayPanel displayPanel)throws EntryWayPanelNotFoundException
    {
        if (entryWayPanelId == null) {
            throw new EntryWayPanelNotFoundException("EntryWay Panel Id is required");
        }


        EntryWayPanel entryWayPanel=entryWayPanelService.addDisplayPanelsToEntryWayPanel(entryWayPanelId,displayPanel);

        logger.info("Display panel added successfully to entry way panel ID: {}", entryWayPanelId);

        return new ResponseEntity<>(entryWayPanel,HttpStatus.OK);
    }
}

