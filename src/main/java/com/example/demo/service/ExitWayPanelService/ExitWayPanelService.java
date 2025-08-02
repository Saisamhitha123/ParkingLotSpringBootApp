package com.example.demo.service.ExitWayPanelService;

import com.example.demo.exception.*;
import com.example.demo.model.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ExitWayPanelService {
    public ParkingLot addExitWayPanels(Long parkingLotId, List<ExitWayPanel> exitWayPanels) throws ParkingLotNotFoundException;
    public DisplayPanel unParkVehicles(Long parkingLotId, Vehicle vehicle) throws ExitWayPanelAlreadyInUseException, VehicleNotFoundException;
    public ExitWayPanel addDisplayPanelsToExitWayPanel(Long exitWayPanelId, DisplayPanel displayPanel) throws ExitWayPanelNotFoundException;
    public List<ExitWayPanel> getAvailableExitPanels(Long parkingLotId) throws ParkingLotNotFoundException;


}
