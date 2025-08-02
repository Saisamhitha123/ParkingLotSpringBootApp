package com.example.demo.service.DisplayPanel;

import com.example.demo.model.dto.ParkingLotDTO;
import com.example.demo.model.entity.ParkingLot;
import org.springframework.stereotype.Service;

@Service
public interface LotDisplayPanel {
    public String displayFloorsCount(ParkingLotDTO parkingLot);
}
