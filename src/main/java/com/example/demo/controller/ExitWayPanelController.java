package com.example.demo.controller;


import com.example.demo.exception.*;
import com.example.demo.model.entity.*;
import com.example.demo.service.ExitWayPanelService.ExitWayPanelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExitWayPanelController {

    private static final Logger logger = LoggerFactory.getLogger(ExitWayPanelController.class);


    @Autowired
    ExitWayPanelService exitWayPanelService;
    /**
     * Adds a list of ExitWayPanels to a parking lot.
     *
     * @param parkingLotId   the ID of the parking lot
     * @param exitWayPanels  list of exit way panels to be added
     * @return updated ParkingLot entity
     * @throws ParkingLotNotFoundException if parking lot ID is missing or not found
     */

    @PostMapping("/v1/parkingLots/{parkingLotId}/exitWayPanels")
    public ResponseEntity<ParkingLot> addExitWayPanels(@PathVariable Long parkingLotId, @RequestBody List<ExitWayPanel> exitWayPanels) throws ParkingLotNotFoundException {
        if (parkingLotId == null) {
            throw new ParkingLotNotFoundException("Parking lot ID is required");
        }

        ParkingLot parkingLotResponse = exitWayPanelService.addExitWayPanels(parkingLotId, exitWayPanels);
        logger.info("Successfully added exit way panels to parking lot ID: {}", parkingLotId);

        return new ResponseEntity<ParkingLot>(parkingLotResponse, HttpStatus.OK);
    }




    /**
     * Unparks a vehicle via a specific ExitWayPanel.
     *
     * @param exitWayPanelId the ID of the exit panel
     * @param vehicle        the vehicle to be unparked
     * @return DisplayPanel reflecting the result
     * @throws VehicleNotFoundException if the vehicle is not found
     * @throws ExitWayPanelAlreadyInUseException if the panel is busy
     */
    @PostMapping("/v1/parkingLots/{exitWayPanelId}/unParkVehicles")
    public ResponseEntity<DisplayPanel> unparkVehicles(@PathVariable Long exitWayPanelId, @RequestBody Vehicle vehicle) throws VehicleNotFoundException, ExitWayPanelAlreadyInUseException {
        DisplayPanel displayPanel=exitWayPanelService.unParkVehicles(exitWayPanelId,vehicle);

        logger.info("Vehicle {} unparked successfully via exit panel ID: {}", vehicle.getVehicleNumber(), exitWayPanelId);

        return new ResponseEntity<>(displayPanel,HttpStatus.OK);
    }

    /**
     * Adds a DisplayPanel to a specific ExitWayPanel.
     *
     * @param exitWayPanelId the ID of the exit panel
     * @param displayPanel   the display panel to be added
     * @return updated ExitWayPanel
     * @throws ExitWayPanelNotFoundException if the exit panel is not found
     */

    @PostMapping("/v1/parkingLots/{exitWayPanelId}/exitDisplayPanels")
    public ResponseEntity<ExitWayPanel> addDisplayPanelsToExitWayPanels(@PathVariable Long exitWayPanelId, @RequestBody DisplayPanel displayPanel)throws ExitWayPanelNotFoundException
    {
        if (exitWayPanelId == null) {
            throw new ExitWayPanelNotFoundException("Exit Panel Id is required");
        }


        ExitWayPanel exitWayPanel=exitWayPanelService.addDisplayPanelsToExitWayPanel(exitWayPanelId,displayPanel);
        logger.info("Display panel added successfully to exit way panel ID: {}", exitWayPanelId);

        return new ResponseEntity<>(exitWayPanel,HttpStatus.OK);
    }


    /**
     * Retrieves all available exit way panels for a given parking lot.
     *
     * @param parkingLotId the ID of the parking lot
     * @return list of ExitWayPanel objects
     * @throws ParkingLotNotFoundException if the parking lot is not found
     * @throws EntryWayPanelNotFoundException (included from shared hierarchy)
     */
    @GetMapping("/v1/parkingLots/{parkingLotId}/exitWayPanels")
    public ResponseEntity<List<ExitWayPanel>> fetchExitWayPanels(@PathVariable Long parkingLotId) throws ParkingLotNotFoundException, EntryWayPanelNotFoundException {
        if (parkingLotId == null) {
            throw new ParkingLotNotFoundException("Parking lot ID is required");
        }

        List<ExitWayPanel> exitWayPanels=exitWayPanelService.getAvailableExitPanels(parkingLotId);
        logger.info("Found {} exit way panels for parking lot ID: {}", exitWayPanels.size(), parkingLotId);

        return new ResponseEntity<>(exitWayPanels,HttpStatus.OK);
    }

}
