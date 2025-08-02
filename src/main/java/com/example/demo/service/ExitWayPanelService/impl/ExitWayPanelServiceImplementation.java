package com.example.demo.service.ExitWayPanelService.impl;

import com.example.demo.exception.*;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.ParkingTicketDTO;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.*;
import com.example.demo.repository.ExitWayPanelRepository;
import com.example.demo.repository.ParkingLotRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.DisplayPanel.DisplayPanelService;
import com.example.demo.service.DisplayPanelOperationService.impl.DisplayPanelOperationServiceImplementation;
import com.example.demo.service.ExitWayPanelService.ExitWayPanelService;
import com.example.demo.service.FeeCalculationService.FeeCalculator;
import com.example.demo.service.FeeCalculationService.impl.LargeSpotFee;
import com.example.demo.service.FeeCalculationService.impl.MediumSpotFee;
import com.example.demo.service.FeeCalculationService.impl.SmallSpotFee;
import com.example.demo.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class ExitWayPanelServiceImplementation implements ExitWayPanelService {

    private static final Logger logger = LoggerFactory.getLogger(ExitWayPanelServiceImplementation.class);


    @Autowired
    ParkingLotRepository parkingLotRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ExitWayPanelRepository exitWayPanelRepository;


    @Autowired
    @Qualifier("parkingLotExitDisplayPanel")
    DisplayPanelService displayPanelService;

    private final Map<Long, AtomicBoolean> inUseMap = new ConcurrentHashMap<>();
    private final Map<Long, Object> lockMap = new ConcurrentHashMap<>();
    /**
     * Adds a list of ExitWayPanels to a ParkingLot.
     *
     * @param parkingLotId   ID of the parking lot
     * @param exitWayPanels  list of exit way panels to add
     * @return updated ParkingLot
     * @throws ParkingLotNotFoundException if parking lot ID is invalid
     */
    public ParkingLot addExitWayPanels(Long parkingLotId, List<ExitWayPanel> exitWayPanels)throws ParkingLotNotFoundException
    {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Lot not found with id: " + parkingLotId));

        for (ExitWayPanel exitWayPanel : exitWayPanels) {
            exitWayPanel.setParkingLot(parkingLot);
        }
        if(parkingLot.getExitWayPanels()==null)
        {
            parkingLot.setExitWayPanels(new ArrayList<>());
        }
        parkingLot.getExitWayPanels().addAll(exitWayPanels);
        logger.info("Successfully added {} exit panels to parking lot {}", exitWayPanels.size(), parkingLotId);

        return parkingLotRepository.save(parkingLot);

    }
    /**
     * Gets all available (not in-use) exit way panels for a parking lot.
     *
     * @param parkingLotId the parking lot ID
     * @return list of available ExitWayPanels
     * @throws ParkingLotNotFoundException if the parking lot is not found
     */
    public List<ExitWayPanel> getAvailableExitPanels(Long parkingLotId)throws ParkingLotNotFoundException
    {
        ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
                .orElseThrow(() -> new ParkingLotNotFoundException("Parking Lot not found with id: " + parkingLotId));
        List<ExitWayPanel> exitWayPanels=parkingLot.getExitWayPanels();
        logger.info("Found {} available exit panels in parking lot {}", exitWayPanels.size(), parkingLotId);

        return exitWayPanels.stream()
                .filter(panel -> {
                    AtomicBoolean flag = inUseMap.get(panel.getExitPanelId());
                    return flag == null || !flag.get(); // Not in use
                })
                .collect(Collectors.toList());

    }
    /**
     * Unparks a vehicle through a specific ExitWayPanel.
     *
     * @param exitWayPanelId ID of the exit way panel
     * @param vehicle        the vehicle to be unparked
     * @return the updated DisplayPanel with message set
     * @throws ExitWayPanelAlreadyInUseException if the panel is already in use
     * @throws VehicleNotFoundException          if the vehicle is not found in DB
     */
    public DisplayPanel unParkVehicles(Long exitWayPanelId, Vehicle vehicle) throws ExitWayPanelAlreadyInUseException, VehicleNotFoundException {

        Object lock = lockMap.computeIfAbsent(exitWayPanelId, id -> new Object());
        AtomicBoolean inUse = inUseMap.computeIfAbsent(exitWayPanelId, id -> new AtomicBoolean(false));
        DisplayPanel displayPanel = null;
        synchronized (lock) {
            if (inUse.get()) {
                throw new ExitWayPanelAlreadyInUseException("Entry panel is already in use");
            }

            try {
                inUse.set(true);


                Optional<ExitWayPanel> panel=exitWayPanelRepository.findById(exitWayPanelId);

                ExitWayPanel exitWayPanel=panel.get();

                String feeType=exitWayPanel.getFeeType().toString();

                Vehicle vehicleTemp = vehicleRepository.findById(vehicle.getVehicleNumber())
                        .orElseThrow(() -> new VehicleNotFoundException(vehicle.getVehicleNumber()));



                FeeCalculator feeCalculator=getRequiredBean(feeType,vehicleTemp);

                BigDecimal amount = feeCalculator.calculateFee(vehicleTemp);

                vehicleTemp.getParkingTicket().setAmountCharged(amount);












                exitWayPanel.getDisplayPanel().setMessage(displayPanelService.display((vehicleTemp)));


                exitWayPanelRepository.save(exitWayPanel);

                displayPanel=exitWayPanel.getDisplayPanel();

                ParkingSpot spot = vehicleTemp.getParkingSpot();
                if (spot != null) {
                    spot.setVehicle(null);
                    spot.setStatus(Status.VACANT);
                }
                vehicleTemp.setParkingSpot(null);

                System.out.println(vehicleTemp.getVehicleNumber());
                vehicleRepository.deleteById(vehicleTemp.getVehicleNumber());





            }
            finally {
                inUse.set(false);
            }
        }
        return displayPanel;

    }

    /**
     * Assigns a display panel to an exit way panel.
     *
     * @param exitWayPanelId ID of the panel
     * @param displayPanel   display panel to assign
     * @return updated ExitWayPanel
     * @throws ExitWayPanelNotFoundException if the exit panel is not found
     */
    public ExitWayPanel addDisplayPanelsToExitWayPanel(Long exitWayPanelId, DisplayPanel displayPanel) throws ExitWayPanelNotFoundException
    {
        ExitWayPanel exitWayPanel = exitWayPanelRepository.findById(exitWayPanelId)
                .orElseThrow(() -> new ExitWayPanelNotFoundException("Exit Way Panel not found with id: " + exitWayPanelId));

        exitWayPanel.setDisplayPanel(displayPanel);
        displayPanel.setExitWayPanel(exitWayPanel);

        return   exitWayPanelRepository.save(exitWayPanel);
    }

    private FeeCalculator getRequiredBean(String feeType,Vehicle vehicle)
    {
        return switch (vehicle.getVehicleType()){
            case TWO_WHEELER -> new SmallSpotFee((FeeCalculator) applicationContext.getBean(feeType));
            case CAR -> new MediumSpotFee((FeeCalculator) applicationContext.getBean(feeType));
            case BUS, TRUCk -> new LargeSpotFee((FeeCalculator) applicationContext.getBean(feeType));
        };
    }
    public VehicleDTO mapToVehicleDTO(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }

        ParkingTicketDTO ticketDTO = null;
        if (vehicle.getParkingTicket() != null) {
            System.out.println("entered");
            ticketDTO = new ParkingTicketDTO.Builder().setTicketId(String.valueOf(vehicle.getParkingTicket().getTicketId())).setAmountCharged( vehicle.getParkingTicket().getAmountCharged()).build();

        }

        ParkingSpotDTO spotDTO = null;
        if (vehicle.getParkingSpot() != null) {
            ParkingSpot spot = vehicle.getParkingSpot();
            spotDTO = new ParkingSpotDTO.Builder().setSpotType(spot.getSpotType()).setUnderMaintenance(spot.isUnderMaintenance()).setStatus(spot.getStatus()).setSpotId(spot.getSpotId()).build();
        }


        VehicleDTO dto = new VehicleDTO(vehicle.getVehicleNumber(), vehicle.getVehicleType(), ticketDTO);

        ticketDTO.setSpot(spotDTO);
        ticketDTO.setVehicle(dto);
      dto.setParkingTicketDTO(ticketDTO);

        return dto;
    }

}
