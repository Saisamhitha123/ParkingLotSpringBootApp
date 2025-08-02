package com.example.demo.service.ParkingFloorService.impl;

import com.example.demo.exception.ParkingFloorCreationException;
import com.example.demo.exception.ParkingFloorNotFoundException;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.ParkingTicketDTO;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.*;
import com.example.demo.repository.ParkingFloorRepository;
import com.example.demo.repository.ParkingLotRepository;
import com.example.demo.service.DisplayPanel.DisplayPanelService;
import com.example.demo.service.DisplayPanelOperationService.impl.DisplayPanelOperationServiceImplementation;
import com.example.demo.service.ParkingFloorService.ParkingFloorService;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingFloorServiceImplementation implements ParkingFloorService {

    @Autowired
    ParkingFloorRepository parkingFloorRepository;
    @Autowired
    ParkingLotRepository parkingLotRepository;
    @Autowired
    @Qualifier(value = "parkingFloorDisplayPanel")

    DisplayPanelService displayPanelService;

    private static final Logger logger = LoggerFactory.getLogger(ParkingFloorServiceImplementation.class);

    /**
     * Adds a list of parking floors to a parking lot.
     *
     * @param parkingLotId ID of the parking lot
     * @param parkingFloor List of ParkingFloor entities to add
     * @return Updated ParkingLot entity
     * @throws ParkingFloorCreationException if any issue occurs during creation
     * @throws ParkingLotNotFoundException if parking lot is not found
     */

    public ParkingLot addParkingFloor(Long parkingLotId, List<ParkingFloor> parkingFloor) throws ParkingFloorCreationException,ParkingLotNotFoundException
    {

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Lot not found with id: " + parkingLotId));

        for (ParkingFloor floor : parkingFloor) {
                floor.setParkingLot(parkingLot);
            }

        if (parkingLot.getParkingFloors() == null) {
            parkingLot.setParkingFloors(new ArrayList<>());
        }

        parkingLot.getParkingFloors().addAll(parkingFloor);
        logger.info("Successfully added {} floor(s) to parking lot ID: {}", parkingFloor.size(), parkingLotId);

            return parkingLotRepository.save(parkingLot);


    }

    /**
     * Fetches all parking floors from a parking lot.
     *
     * @param parkingLotId ID of the parking lot
     * @return List of ParkingFloor entities
     * @throws ParkingLotNotFoundException if parking lot is not found
     */
    public List<ParkingFloor> fetchParkingFloors(Long parkingLotId) throws ParkingLotNotFoundException
    {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Lot not found with id: " + parkingLotId));

        List<ParkingFloor> parkingFloors=parkingLot.getParkingFloors();

        return parkingFloors;

    }
    /**
     * Adds a display panel to a parking floor and updates display message.
     *
     * @param parkingFloorId ID of the parking floor
     * @param displayPanel DisplayPanel to be added
     * @return Updated ParkingFloor entity
     * @throws ParkingFloorNotFoundException if floor not found
     */
    public ParkingFloor addDisplayPanelToParkingFloor(Long parkingFloorId, DisplayPanel displayPanel) throws ParkingFloorNotFoundException
    {
        ParkingFloor parkingFloor = parkingFloorRepository.findById(parkingFloorId)
                .orElseThrow(() -> new ParkingFloorNotFoundException("Parking Floor not found with id: " + parkingFloorId));
        parkingFloor.setDisplayPanel(displayPanel);
        displayPanel.setParkingFloor(parkingFloor);



        ParkingFloorDTO parkingFloorDTO=mapToDTO(parkingFloor);


        displayPanel.setMessage(displayPanelService.display(parkingFloorDTO));
       return parkingFloorRepository.save(parkingFloor);
    }

    public ParkingFloorDTO mapToDTO(ParkingFloor floor) {
        Map<Status, Map<SpotType, List<ParkingSpotDTO>>> groupedSpots =
                floor.getParkingSpots().stream()
                        .map(this::mapToDTO)
                        .collect(Collectors.groupingBy(
                                ParkingSpotDTO::getStatus,
                                Collectors.groupingBy(
                                        ParkingSpotDTO::getSpotType
                                )
                        ));

        return new ParkingFloorDTO.ParkingFloorDTOBuilder().setParkingSpots(groupedSpots).setFloorNumber(String.valueOf(floor.getFloorId())).setDisplayPanel(floor.getDisplayPanel()).setStatus(floor.getStatus()).setIsUnderMaintenance(floor.isUnderMaintenance()).build();


    }

    public ParkingSpotDTO mapToDTO(ParkingSpot spot) {

        Vehicle vehicle = spot.getVehicle();
        VehicleDTO vehicleDTO = null;

        if (vehicle != null) {
            ParkingTicket ticket = vehicle.getParkingTicket();
            ParkingTicketDTO ticketDTO = null;

            if (ticket != null) {
                ticketDTO = new ParkingTicketDTO.Builder().setTicketId(String.valueOf(ticket.getTicketId())).build();

            }

            vehicleDTO = new VehicleDTO(
                    vehicle.getVehicleNumber(),
                    vehicle.getVehicleType(),
                    ticketDTO
            );

            ticketDTO.setVehicle(vehicleDTO);
            ticketDTO.setAmountCharged(ticket.getAmountCharged());
            vehicleDTO.setParkingTicketDTO(ticketDTO);
        }




        return new ParkingSpotDTO.Builder().setSpotType(spot.getSpotType()).setVehicle(vehicleDTO).setSpotId(spot.getSpotId()).setUnderMaintenance(spot.isUnderMaintenance()).setStatus(spot.getStatus()).build();

    }
    /**
     * Adds a list of parking spots to the given parking floor.
     *
     * @param parkingFloorId ID of the parking floor
     * @param parkingSpots List of ParkingSpot entities
     * @return Updated ParkingFloor entity
     * @throws ParkingLotNotFoundException if floor is not found
     */
    public  ParkingFloor addParkingSpotToParkingFloor(Long parkingFloorId,List<ParkingSpot> parkingSpots) throws ParkingLotNotFoundException {
        ParkingFloor parkingFloor = parkingFloorRepository.findById(parkingFloorId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Floor not found with id: " + parkingFloorId));

        for(ParkingSpot spot:parkingSpots)
        {
            spot.setParkingFloor(parkingFloor);
        }


        if(parkingFloor.getParkingSpots()==null)
        {
            parkingFloor.setParkingSpots(new ArrayList<>());
        }
        parkingFloor.setParkingSpots(parkingSpots);

        return parkingFloorRepository.save(parkingFloor);



    }


}
