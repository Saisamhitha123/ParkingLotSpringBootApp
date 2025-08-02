package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingLotDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.service.DisplayPanel.*;
import org.springframework.stereotype.Service;

@Service("parkingLotDisplayPanel")
public class ParkingLotDisplayPanel extends DisplayPanelService<ParkingLotDTO> {

    public ParkingLotDisplayPanel(EntryDisplayPanel entryDisplayPanel, ExitDisplayPanel exitDisplayPanel, FloorDisplayPanel floorDisplayPanel, LotDisplayPanel lotDisplayPanel) {
        super(entryDisplayPanel, exitDisplayPanel, floorDisplayPanel, lotDisplayPanel);
    }

    @Override
    public String display(ParkingLotDTO parkingLotDTO) {
        return "Welcome to ParkCentral Parking Lot "+lotDisplayPanel.displayFloorsCount(parkingLotDTO);
    }
}
