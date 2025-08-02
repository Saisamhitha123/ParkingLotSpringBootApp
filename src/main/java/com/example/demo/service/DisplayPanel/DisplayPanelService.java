package com.example.demo.service.DisplayPanel;

import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingLotDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.VehicleDTO;
import org.springframework.stereotype.Service;

@Service
public abstract class DisplayPanelService<T> {
    protected EntryDisplayPanel entryDisplayPanel;
    protected ExitDisplayPanel exitDisplayPanel;
    protected FloorDisplayPanel floorDisplayPanel;
    protected LotDisplayPanel lotDisplayPanel;


    public DisplayPanelService(EntryDisplayPanel entryDisplayPanel, ExitDisplayPanel exitDisplayPanel, FloorDisplayPanel floorDisplayPanel, LotDisplayPanel lotDisplayPanel) {
        this.entryDisplayPanel = entryDisplayPanel;
        this.exitDisplayPanel = exitDisplayPanel;
        this.floorDisplayPanel = floorDisplayPanel;
        this.lotDisplayPanel = lotDisplayPanel;


    }

    public abstract String display(T obj);

}
