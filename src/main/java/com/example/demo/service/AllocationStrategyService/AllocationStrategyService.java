package com.example.demo.service.AllocationStrategyService;

import com.example.demo.model.entity.ParkingLot;
import com.example.demo.model.entity.ParkingSpot;
import com.example.demo.model.entity.Vehicle;
import org.springframework.stereotype.Service;

@Service
public interface AllocationStrategyService {
    public ParkingSpot vacantSpot(Long parkingLotId, Vehicle vehicle);
}
