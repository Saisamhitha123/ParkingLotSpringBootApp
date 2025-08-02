package com.example.demo.service.DisplayPanel;

import com.example.demo.model.dto.ParkingLotDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import org.springframework.stereotype.Service;

@Service
public interface  EntryDisplayPanel {

    public  String displayAllocatedSpot(ParkingSpotDTO parkingSpotDTO);
}
