package com.example.demo.service.ParkingFloorService;

import com.example.demo.exception.ParkingFloorCreationException;
import com.example.demo.exception.ParkingFloorNotFoundException;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.model.entity.ParkingFloor;
import com.example.demo.model.entity.ParkingLot;
import com.example.demo.model.entity.ParkingSpot;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ParkingFloorService {
    public ParkingLot addParkingFloor(Long parkingLotId, List<ParkingFloor> parkingFloor) throws ParkingFloorCreationException, ParkingLotNotFoundException;
    public List<ParkingFloor> fetchParkingFloors(Long parkingLotdId) throws ParkingLotNotFoundException;
    public  ParkingFloor addDisplayPanelToParkingFloor(Long parkingFloorId, DisplayPanel displayPanel) throws ParkingFloorNotFoundException;
    public  ParkingFloor addParkingSpotToParkingFloor(Long parkingFloorId,List<ParkingSpot> parkingSpots) throws ParkingLotNotFoundException;
}
