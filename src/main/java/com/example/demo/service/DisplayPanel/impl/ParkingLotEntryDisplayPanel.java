package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.model.dto.*;
import com.example.demo.service.DisplayPanel.*;
import org.springframework.stereotype.Service;

@Service("parkingLotEntryDisplayPanel")
public class ParkingLotEntryDisplayPanel extends DisplayPanelService<ParkingSpotDTO> {
    public ParkingLotEntryDisplayPanel(EntryDisplayPanel entryDisplayPanel, ExitDisplayPanel exitDisplayPanel, FloorDisplayPanel floorDisplayPanel, LotDisplayPanel lotDisplayPanel) {
        super(entryDisplayPanel, exitDisplayPanel, floorDisplayPanel, lotDisplayPanel);
    }

    @Override
    public String display(ParkingSpotDTO parkingSpotDTO) {
        return entryDisplayPanel.displayAllocatedSpot(parkingSpotDTO);
    }
}
