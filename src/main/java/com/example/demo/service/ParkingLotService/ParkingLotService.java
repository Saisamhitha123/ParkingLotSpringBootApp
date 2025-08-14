package com.example.demo.service.ParkingLotService;

import com.example.demo.exception.ParkingLotCreationException;
import com.example.demo.model.entity.*;

import java.util.List;

public interface ParkingLotService {
    public ParkingLot addParkingLot(ParkingLot parkingLot) throws ParkingLotCreationException;

}
