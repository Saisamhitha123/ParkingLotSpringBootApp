package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingLotDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.Vehicle;
import com.example.demo.service.DisplayPanel.*;
import org.springframework.stereotype.Service;

@Service("parkingLotExitDisplayPanel")
public class ParkingLotExitDisplayPanel extends DisplayPanelService<Vehicle> {
    public ParkingLotExitDisplayPanel(EntryDisplayPanel entryDisplayPanel, ExitDisplayPanel exitDisplayPanel, FloorDisplayPanel floorDisplayPanel, LotDisplayPanel lotDisplayPanel) {
        super(entryDisplayPanel, exitDisplayPanel, floorDisplayPanel, lotDisplayPanel);
    }

    @Override
    public String display(Vehicle vehicle) {
        return exitDisplayPanel.displayFeeWithVehicleNumber(vehicle);
    }
}
