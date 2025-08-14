package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.controller.VehicleController;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.service.DisplayPanel.EntryDisplayPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EntryDisplayPanelImplementatation implements EntryDisplayPanel {

    private static final Logger logger = LoggerFactory.getLogger(EntryDisplayPanelImplementatation.class);

    @Override
    public String displayAllocatedSpot(ParkingSpotDTO parkingSpotDTO) {

        return parkingSpotDTO.getSpotId().toString();
    }
}
