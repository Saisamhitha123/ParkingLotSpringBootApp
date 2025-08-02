package com.example.demo.model.dto;

import com.example.demo.model.entity.ParkingSpot;
import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;

import java.util.List;
import java.util.Map;


public class ParkingFloorDTO {

    private Map<Status, Map<SpotType, List<ParkingSpotDTO>>> parkingSpots;
    private String floorNumber;
    private DisplayPanel displayPanel;
    private Status status;
    private boolean isUnderMaintenance;

    public ParkingFloorDTO(ParkingFloorDTOBuilder parkingFloorDTOBuilder)

    {
this.parkingSpots=parkingFloorDTOBuilder.parkingSpots;
this.floorNumber=parkingFloorDTOBuilder.floorNumber;
this.displayPanel=parkingFloorDTOBuilder.displayPanel;
this.status=parkingFloorDTOBuilder.status;
this.isUnderMaintenance=parkingFloorDTOBuilder.isUnderMaintenance;
    }

    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        isUnderMaintenance = underMaintenance;
    }

    public Map<Status, Map<SpotType, List<ParkingSpotDTO>>> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(Map<Status, Map<SpotType, List<ParkingSpotDTO>>> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

public static class ParkingFloorDTOBuilder{

    private Map<Status, Map<SpotType, List<ParkingSpotDTO>>> parkingSpots;
    private String floorNumber;
    private DisplayPanel displayPanel;
    private Status status;
    private boolean isUnderMaintenance;


    public ParkingFloorDTOBuilder setParkingSpots(Map<Status, Map<SpotType, List<ParkingSpotDTO>>> parkingSpots) {
        this.parkingSpots = parkingSpots;
        return this;
    }

    public ParkingFloorDTOBuilder setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
        return this;
    }

    public ParkingFloorDTOBuilder setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
        return this;
    }

    public ParkingFloorDTOBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public ParkingFloorDTOBuilder setIsUnderMaintenance(boolean isUnderMaintenance) {
        this.isUnderMaintenance = isUnderMaintenance;
        return this;
    }
    public ParkingFloorDTO build() {
      return new ParkingFloorDTO(this);
    }


}
}
