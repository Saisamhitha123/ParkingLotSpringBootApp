package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingLotDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.model.entity.ParkingFloor;
import com.example.demo.service.DisplayPanel.*;
import org.springframework.stereotype.Service;

@Service("parkingFloorDisplayPanel")
public class ParkingFloorDisplayPanel extends DisplayPanelService<ParkingFloorDTO>
{

    public ParkingFloorDisplayPanel(EntryDisplayPanel entryDisplayPanel, ExitDisplayPanel exitDisplayPanel, FloorDisplayPanel floorDisplayPanel, LotDisplayPanel lotDisplayPanel) {
        super(entryDisplayPanel, exitDisplayPanel, floorDisplayPanel, lotDisplayPanel);
    }

    @Override
    public String display(ParkingFloorDTO parkingFloorDTO) {
        return floorDisplayPanel.displayFloorStatus(parkingFloorDTO);
    }
}
