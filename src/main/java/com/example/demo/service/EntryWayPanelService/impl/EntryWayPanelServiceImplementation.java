package com.example.demo.service.EntryWayPanelService.impl;

import com.example.demo.exception.EntryPanelAlreadyInUseException;
import com.example.demo.exception.EntryWayPanelNotFoundException;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.ParkingTicketDTO;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.*;
import com.example.demo.repository.EntryWayPanelRepository;
import com.example.demo.repository.ParkingLotRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.AllocationStrategyService.AllocationStrategyService;
import com.example.demo.service.DisplayPanel.DisplayPanelService;
import com.example.demo.service.DisplayPanelOperationService.impl.DisplayPanelOperationServiceImplementation;
import com.example.demo.service.EntryWayPanelService.EntryWayPanelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class EntryWayPanelServiceImplementation implements EntryWayPanelService {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    EntryWayPanelRepository entryWayPanelRepository;

    @Autowired
    @Qualifier("parkingLotEntryDisplayPanel")
    DisplayPanelService displayPanelService;

    private final Map<Long, AtomicBoolean> inUseMap = new ConcurrentHashMap<>();
    private final Map<Long, Object> lockMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(EntryWayPanelServiceImplementation.class);

    /**
     * Adds entry way panels to an existing parking lot.
     *
     * @param parkingLotId     ID of the parking lot
     * @param entryWayPanels   List of entry panels to add
     * @return Updated ParkingLot with added entry panels
     * @throws ParkingLotNotFoundException if parking lot is not found
     */
    public ParkingLot addEntryWayPanels(Long parkingLotId, List<EntryWayPanel> entryWayPanels) throws ParkingLotNotFoundException {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Lot not found with id: " + parkingLotId));

        for (EntryWayPanel entryWayPanel : entryWayPanels) {
            entryWayPanel.setParkingLot(parkingLot);
        }
        if (parkingLot.getExitWayPanels() == null) {
            parkingLot.setEntryWayPanels(new ArrayList<>());
        }
        parkingLot.getEntryWayPanels().addAll(entryWayPanels);
        logger.info("Entry panels added: {}", entryWayPanels.size());

        return parkingLotRepository.save(parkingLot);

    }
    /**
     * Fetches all entry panels for a given parking lot.
     *
     * @param parkingLotId ID of the parking lot
     * @return List of EntryWayPanel
     * @throws EntryWayPanelNotFoundException if parking lot is not found
     */

    public List<EntryWayPanel> fetchEntryWayPanels(Long parkingLotId) throws EntryWayPanelNotFoundException {
        logger.info("Fetching entry panels for parking lot ID: {}", parkingLotId);

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new EntryWayPanelNotFoundException("Parking Lot not found with id: " + parkingLotId));

        List<EntryWayPanel> entryWayPanels = parkingLot.getEntryWayPanels();

        return entryWayPanels;
    }
    /**
     * Assigns a display panel to an entry panel.
     *
     * @param entryWayPaneIId ID of the entry panel
     * @param displayPanel    Display panel to attach
     * @return Updated EntryWayPanel with display panel
     * @throws EntryWayPanelNotFoundException if entry panel is not found
     */

    public EntryWayPanel addDisplayPanelsToEntryWayPanel(Long entryWayPaneIId, DisplayPanel displayPanel) throws EntryWayPanelNotFoundException {
        EntryWayPanel entryWayPanel = entryWayPanelRepository.findById(entryWayPaneIId)
                .orElseThrow(() -> new EntryWayPanelNotFoundException("Entry Way Panel not found with id: " + entryWayPaneIId));
        logger.info("Adding display panel to entry panel ID: {}", entryWayPaneIId);

        entryWayPanel.setDisplayPanel(displayPanel);
        displayPanel.setEntryWayPanel(entryWayPanel);

        return entryWayPanelRepository.save(entryWayPanel);
    }

    /**
     * Returns entry panels not currently in use.
     *
     * @param parkingLotId ID of the parking lot
     * @return List of available EntryWayPanels
     * @throws ParkingLotNotFoundException if parking lot is not found
     */

    public List<EntryWayPanel> getAvailablePanels(Long parkingLotId) throws ParkingLotNotFoundException {
        logger.info("Getting available entry panels for lot ID: {}", parkingLotId);

        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Lot not found with id: " + parkingLotId));
        List<EntryWayPanel> entryWayPanels = parkingLot.getEntryWayPanels();
        return entryWayPanels.stream()
                .filter(panel -> {
                    AtomicBoolean flag = inUseMap.get(panel.getEntryPanelId());
                    return flag == null || !flag.get(); // Not in use
                })
                .collect(Collectors.toList());

    }

    /**
     * Attempts to allocate a parking spot and update the display panel.
     * Thread-safe using lock per entryPanelId.
     *
     * @param entryPanelId ID of the entry panel
     * @param vehicle      Vehicle to park
     * @return DisplayPanel with allocation message
     * @throws EntryPanelAlreadyInUseException if the panel is in use
     * @throws EntryWayPanelNotFoundException  if entry panel not found
     */
    public DisplayPanel parkVehicle(Long entryPanelId, Vehicle vehicle) throws EntryPanelAlreadyInUseException, EntryWayPanelNotFoundException {
        Object lock = lockMap.computeIfAbsent(entryPanelId, id -> new Object());
        AtomicBoolean inUse = inUseMap.computeIfAbsent(entryPanelId, id -> new AtomicBoolean(false));
        DisplayPanel displayPanel = null;
        synchronized (lock) {
            if (inUse.get()) {
                throw new EntryPanelAlreadyInUseException("Entry panel is already in use");
            }

            try {
                inUse.set(true);

                EntryWayPanel entryPanel = entryWayPanelRepository.findById(entryPanelId)
                        .orElseThrow(() -> new EntryWayPanelNotFoundException("Entry panel not found"));

                AllocationStrategyService allocationStrategyService = (AllocationStrategyService) applicationContext.getBean(entryPanel.getStrategyType().toString());

                ParkingSpot spot = allocationStrategyService.vacantSpot(entryPanel.getParkingLot().getParkingLotId(), vehicle);

                ParkingSpotDTO parkingSpotDTO = mapToDTO(spot);


                String message = displayPanelService.display(parkingSpotDTO);

                if (entryPanel.getDisplayPanel() == null) {
                    DisplayPanel panel = new DisplayPanel();
                    entryPanel.setDisplayPanel(panel);
                }
                entryPanel.getDisplayPanel().setMessage(message);
                entryWayPanelRepository.save(entryPanel);


            } finally {
                inUse.set(false);
            }
        }
        return displayPanel;
    }

    /**
     * Converts a ParkingSpot entity to ParkingSpotDTO.
     *
     * @param spot ParkingSpot entity
     * @return ParkingSpotDTO
     */

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

        return new ParkingSpotDTO.Builder().setSpotId(spot.getSpotId()).setVehicle(vehicleDTO).setUnderMaintenance(spot.isUnderMaintenance()).setStatus(spot.getStatus()).setSpotType(spot.getSpotType()).build();

    }
}
