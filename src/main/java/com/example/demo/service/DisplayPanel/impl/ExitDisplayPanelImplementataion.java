package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.controller.VehicleController;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.Vehicle;
import com.example.demo.service.DisplayPanel.ExitDisplayPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ExitDisplayPanelImplementataion implements ExitDisplayPanel {

    private static final Logger logger = LoggerFactory.getLogger(ExitDisplayPanelImplementataion.class);

    @Override
    public String displayFeeWithVehicleNumber(Vehicle vehicle) {
        return vehicle.getVehicleNumber()+" "+vehicle.getParkingTicket().getAmountCharged();
    }
}
