package com.example.demo.service.DisplayPanelOperationService;

import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.model.entity.ParkingLot;
import org.springframework.stereotype.Service;

@Service
public interface DisplayPanelOperationService {
    public ParkingLot addDisplayPanel(Long parkingLotId, DisplayPanel displayPanel) throws ParkingLotNotFoundException;

}
