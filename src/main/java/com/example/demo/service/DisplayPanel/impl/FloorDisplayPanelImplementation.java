package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.controller.VehicleController;
import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.service.DisplayPanel.FloorDisplayPanel;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FloorDisplayPanelImplementation implements FloorDisplayPanel {

    private static final Logger logger = LoggerFactory.getLogger(FloorDisplayPanelImplementation.class);

    public String displayFloorStatus(ParkingFloorDTO parkingFloorDTO)
    {
        StringBuilder s=new StringBuilder();
        if(parkingFloorDTO.isUnderMaintenance())
        {
            return "Floor is under maintenance";
        }
        else {

            s.append("Floor Number is"+parkingFloorDTO.getFloorNumber());

            for (Map.Entry<Status, Map<SpotType, List<ParkingSpotDTO>>> typeEntry : parkingFloorDTO.getParkingSpots().entrySet()) {
                Status spotType = typeEntry.getKey();
                s.append("  SpotType: ").append(spotType).append("\n");

                for (Map.Entry<SpotType, List<ParkingSpotDTO>> statusEntry : typeEntry.getValue().entrySet()) {
                    SpotType status = statusEntry.getKey();
                    List<ParkingSpotDTO> spots = statusEntry.getValue();

                    s.append("    Status: ").append(status).append(" → Spots: ");

                    String spotDetails = spots.stream()
                            .map(spot -> String.valueOf(spot.getSpotId())) // or other fields you want
                            .collect(Collectors.joining(", "));

                    s.append(spotDetails).append("\n");
                }
            }
        }

return s.toString();


    }
}
