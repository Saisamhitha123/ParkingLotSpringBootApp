package com.example.demo.model.entity;


import com.example.demo.util.BooleanToStringConverter;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.engine.internal.Cascade;

import java.util.List;
import java.util.Map;

@Entity
public class ParkingFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long floorId;
    @OneToMany(mappedBy="parkingFloor",cascade = CascadeType.ALL)
    private List<ParkingSpot> parkingSpots;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status is required")
    private Status status;

    public void setFloorId(Long floorId) {
        this.floorId = floorId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @OneToOne(mappedBy="parkingFloor",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private DisplayPanel displayPanel;
    @ManyToOne
    @JsonIgnore
    private ParkingLot parkingLot;

    @Convert(converter = BooleanToStringConverter.class)
    private boolean isUnderMaintenance;

    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        isUnderMaintenance = underMaintenance;
    }

    public ParkingFloor() {

    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public Long getFloorId() {
        return floorId;
    }

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

}
