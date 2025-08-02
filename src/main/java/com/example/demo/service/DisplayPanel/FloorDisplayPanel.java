package com.example.demo.service.DisplayPanel;

import com.example.demo.model.dto.ParkingFloorDTO;
import org.springframework.stereotype.Service;

@Service
public interface FloorDisplayPanel {
    public String displayFloorStatus(ParkingFloorDTO parkingFloorDTO);
}
