package com.example.demo.model.dto;

import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.model.entity.ParkingLot;
import com.example.demo.util.Status;

import java.util.List;
import java.util.Map;

public class ParkingLotDTO {
    private Map<Status, List<ParkingFloorDTO>> parkingFloors;
    private List<EntryWayPanelDTO> entryWayPanels;
    private List<ExitWayPanelDTO> exitWayPanels;
    private DisplayPanel displayPanel;


    // ✅ Constructor that uses Builder
    private ParkingLotDTO(Builder builder) {
        this.parkingFloors = builder.parkingFloors;
        this.entryWayPanels = builder.entryWayPanels;
        this.exitWayPanels = builder.exitWayPanels;
        this.displayPanel = builder.displayPanel;
    }

    public Map<Status, List<ParkingFloorDTO>> getParkingFloors() {
        return parkingFloors;
    }

    public void setParkingFloors(Map<Status, List<ParkingFloorDTO>> parkingFloors) {
        this.parkingFloors = parkingFloors;
    }

    public List<EntryWayPanelDTO> getEntryWayPanels() {
        return entryWayPanels;
    }

    public void setEntryWayPanels(List<EntryWayPanelDTO> entryWayPanels) {
        this.entryWayPanels = entryWayPanels;
    }

    public List<ExitWayPanelDTO> getExitWayPanels() {
        return exitWayPanels;
    }

    public void setExitWayPanels(List<ExitWayPanelDTO> exitWayPanels) {
        this.exitWayPanels = exitWayPanels;
    }

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    // ✅ Builder Class
    public static class Builder {
        private Map<Status, List<ParkingFloorDTO>> parkingFloors;
        private List<EntryWayPanelDTO> entryWayPanels;
        private List<ExitWayPanelDTO> exitWayPanels;
        private DisplayPanel displayPanel;

        public Builder setParkingFloors(Map<Status, List<ParkingFloorDTO>> parkingFloors) {
            this.parkingFloors = parkingFloors;
            return this;
        }

        public Builder setEntryWayPanels(List<EntryWayPanelDTO> entryWayPanels) {
            this.entryWayPanels = entryWayPanels;
            return this;
        }

        public Builder setExitWayPanels(List<ExitWayPanelDTO> exitWayPanels) {
            this.exitWayPanels = exitWayPanels;
            return this;
        }

        public Builder setDisplayPanel(DisplayPanel displayPanel) {
            this.displayPanel = displayPanel;
            return this;
        }

        public ParkingLotDTO build() {
            return new ParkingLotDTO(this);
        }
    }
}
