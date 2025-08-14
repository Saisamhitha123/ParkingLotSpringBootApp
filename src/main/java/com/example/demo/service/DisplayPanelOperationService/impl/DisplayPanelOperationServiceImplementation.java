package com.example.demo.service.DisplayPanelOperationService.impl;

import com.example.demo.controller.VehicleController;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.model.entity.ParkingFloor;
import com.example.demo.model.entity.ParkingLot;
import com.example.demo.repository.ParkingLotRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.DisplayPanel.DisplayPanelService;
import com.example.demo.service.DisplayPanelOperationService.DisplayPanelOperationService;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class DisplayPanelOperationServiceImplementation implements DisplayPanelOperationService {


    private static final Logger logger = LoggerFactory.getLogger(DisplayPanelOperationServiceImplementation.class);


    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    ApplicationContext applicationContext;


    @Autowired
    @Qualifier(value = "parkingLotDisplayPanel")
    DisplayPanelService displayPanelService;
    /**
     * Adds a display panel to a specific parking lot and updates the display message.
     *
     * @param parkingLotId  the ID of the parking lot
     * @param displayPanel  the display panel entity to be added
     * @return updated ParkingLot with the new display panel
     * @throws ParkingLotNotFoundException if the parking lot is not found
     */
    public ParkingLot addDisplayPanel(Long parkingLotId, DisplayPanel displayPanel) throws ParkingLotNotFoundException
    {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Lot not found with id: " + parkingLotId));


        Map<Status, List<ParkingFloorDTO>> statusListMap =  Optional.ofNullable(parkingLot.getParkingFloors())
                .orElse(Collections.emptyList())
                .stream()
                .map(this::mapToDTO)
                .filter(Objects::nonNull) // Filter out null DTOs
                .filter(dto -> dto.getStatus() != null)
                .collect(Collectors.groupingBy(ParkingFloorDTO::getStatus));


        List<EntryWayPanelDTO> entryWayPanelDTOS = Optional.ofNullable(parkingLot.getEntryWayPanels())
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull) // ensure panel is not null
                .map(p -> new EntryWayPanelDTO(p.getDisplayPanel()))
                .filter(Objects::nonNull) // in case constructor returns null
                .toList();



        List<ExitWayPanelDTO> exitWayPanelDTOS = Optional.ofNullable(parkingLot.getExitWayPanels())
                .orElse(Collections.emptyList())
                .stream()
                .filter(Objects::nonNull) // ensure panel is not null
                .map(p -> new ExitWayPanelDTO(p.getDisplayPanel()))
                .filter(Objects::nonNull) // in case constructor returns null
                .toList();




        ParkingLotDTO parkingLotDTO=new ParkingLotDTO.Builder().
                setParkingFloors(statusListMap).
                setEntryWayPanels(entryWayPanelDTOS).
                setExitWayPanels(exitWayPanelDTOS).
                setDisplayPanel(parkingLot.getDisplayPanel()).
                build();

        displayPanel.setParkingLot(parkingLot);
        displayPanel.setMessage(displayPanelService.display(parkingLotDTO));
        parkingLot.setDisplayPanel(displayPanel);
        logger.info("Saved updated parking lot with ID {} including display panel", parkingLotId);

        return parkingLotRepository.save(parkingLot);

    }

    /**
     * Maps a ParkingFloor entity to ParkingFloorDTO with spot grouping by Status and SpotType.
     *
     * @param p the ParkingFloor entity
     * @return ParkingFloorDTO
     */
    private ParkingFloorDTO mapToDTO(ParkingFloor p)
    {

        Map<Status, Map<SpotType, List<ParkingSpotDTO>>> map = p.getParkingSpots().stream()
                .map(spot -> new ParkingSpotDTO.Builder().setSpotType(spot.getSpotType()).
                        setUnderMaintenance(spot.isUnderMaintenance()).setStatus(spot.getStatus()).setSpotId(spot.getSpotId()).build())
                .collect(Collectors.groupingBy(ParkingSpotDTO::getStatus, Collectors.groupingBy(ParkingSpotDTO::getSpotType)));

        ParkingFloorDTO parkingFloorDTO =new ParkingFloorDTO.ParkingFloorDTOBuilder().setFloorNumber(p.getFloorId().toString()).setParkingSpots(map).setDisplayPanel(p.getDisplayPanel())
                .setIsUnderMaintenance(p.isUnderMaintenance()).setStatus(p.getStatus()).build();

        return parkingFloorDTO;


    }
}
