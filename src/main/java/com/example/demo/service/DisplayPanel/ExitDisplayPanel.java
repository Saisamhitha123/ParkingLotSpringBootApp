package com.example.demo.service.DisplayPanel;

import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.Vehicle;
import org.springframework.stereotype.Service;

@Service
public interface ExitDisplayPanel {
    public String displayFeeWithVehicleNumber(Vehicle vehicle);
}
