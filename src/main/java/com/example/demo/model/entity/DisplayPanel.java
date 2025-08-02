package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class DisplayPanel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long panelId;
    private String message;

    @OneToOne
    @JsonIgnore
    private ParkingLot parkingLot;

    @OneToOne
    @JsonIgnore
    private EntryWayPanel entryWayPanel;

    @OneToOne
    @JsonIgnore
    private ExitWayPanel exitWayPanel;

    @OneToOne
    @JsonIgnore
    private ParkingFloor parkingFloor;
    private String name;
    public DisplayPanel() {


    }

    public long getPanelId() {
        return panelId;
    }

    public void setPanelId(long panelId) {
        this.panelId = panelId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public EntryWayPanel getEntryWayPanel() {
        return entryWayPanel;
    }

    public void setEntryWayPanel(EntryWayPanel entryWayPanel) {
        this.entryWayPanel = entryWayPanel;
    }

    public ExitWayPanel getExitWayPanel() {
        return exitWayPanel;
    }

    public void setExitWayPanel(ExitWayPanel exitWayPanel) {
        this.exitWayPanel = exitWayPanel;
    }

    public ParkingFloor getParkingFloor() {
        return parkingFloor;
    }

    public void setParkingFloor(ParkingFloor parkingFloor) {
        this.parkingFloor = parkingFloor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
