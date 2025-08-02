package com.example.demo.service.EntryWayPanelService;

import com.example.demo.exception.EntryPanelAlreadyInUseException;
import com.example.demo.exception.EntryWayPanelNotFoundException;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.entity.*;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface EntryWayPanelService {
    public ParkingLot addEntryWayPanels(Long parkingLotId, List<EntryWayPanel> entryWayPanels) throws ParkingLotNotFoundException;
    public DisplayPanel parkVehicle(Long parkingLotId, Vehicle vehicle)throws EntryWayPanelNotFoundException, EntryPanelAlreadyInUseException;
    public List<EntryWayPanel> getAvailablePanels(Long parkingLotId) throws ParkingLotNotFoundException;
    public EntryWayPanel addDisplayPanelsToEntryWayPanel(Long entryWayPanelId, DisplayPanel displayPanel) throws EntryWayPanelNotFoundException;

}
