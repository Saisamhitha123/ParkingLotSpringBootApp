package com.example.demo.service.DisplayPanel.impl;

import com.example.demo.controller.VehicleController;
import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingLotDTO;
import com.example.demo.model.entity.ParkingFloor;
import com.example.demo.model.entity.ParkingLot;
import com.example.demo.service.DisplayPanel.LotDisplayPanel;
import com.example.demo.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LotDisplayPanelImplementation implements LotDisplayPanel {

    private static final Logger logger = LoggerFactory.getLogger(LotDisplayPanelImplementation.class);

    @Override
    public String displayFloorsCount(ParkingLotDTO parkingLot) {

        StringBuilder result = new StringBuilder();
        Map<Status, List<ParkingFloorDTO>> statusListMap =
                Optional.ofNullable(parkingLot)
                        .map(ParkingLotDTO::getParkingFloors)
                        .orElse(Collections.emptyMap());

        for (Map.Entry<Status, List<ParkingFloorDTO>> entry : statusListMap.entrySet()) {
            Status status = entry.getKey();
            List<ParkingFloorDTO> floors = Optional.ofNullable(entry.getValue()).orElse(Collections.emptyList());
            result.append(status).append(":").append(floors.size()).append("\n");
        }

return result.toString();
    }
}
