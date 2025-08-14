package com.example.demo.model.entity;



import com.example.demo.util.Status;
import jakarta.persistence.*;
import org.hibernate.engine.internal.Cascade;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class ParkingLot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parkingLotId;
    @OneToMany(mappedBy = "parkingLot",cascade = CascadeType.ALL)

    private List<ParkingFloor> parkingFloors;
    @OneToMany(mappedBy = "parkingLot",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<EntryWayPanel> entryWayPanels;
    @OneToMany(mappedBy = "parkingLot",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private List<ExitWayPanel> exitWayPanels;
    @OneToOne(mappedBy = "parkingLot",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private DisplayPanel displayPanel;

    public ParkingLot() {

    }

    public long getParkingLotId() {
        return parkingLotId;
    }

    public List<ParkingFloor> getParkingFloors() {
        return parkingFloors;
    }

    public void setParkingFloors(List<ParkingFloor> parkingFloors) {
        this.parkingFloors = parkingFloors;
    }

    public List<EntryWayPanel> getEntryWayPanels() {
        return entryWayPanels;
    }

    public void setEntryWayPanels(List<EntryWayPanel> entryWayPanels) {
        this.entryWayPanels = entryWayPanels;
    }

    public List<ExitWayPanel> getExitWayPanels() {
        return exitWayPanels;
    }

    public void setExitWayPanels(List<ExitWayPanel> exitWayPanels) {
        this.exitWayPanels = exitWayPanels;
    }

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }
}
