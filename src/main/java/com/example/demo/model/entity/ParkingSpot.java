package com.example.demo.model.entity;

import com.example.demo.util.BooleanToStringConverter;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ParkingSpot {
    @Enumerated(EnumType.STRING)
    private SpotType spotType;
    @OneToOne(mappedBy = "parkingSpot",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Vehicle vehicle;
    @Convert(converter = BooleanToStringConverter.class)
    private boolean isUnderMaintenance;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long spotId;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    @JsonIgnore
    private ParkingFloor parkingFloor;
    public ParkingSpot(SpotType spotType, boolean isUnderMaintenance) {
        this.spotType = spotType;

        this.isUnderMaintenance = isUnderMaintenance;
    }

    public ParkingSpot() {
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        isUnderMaintenance = underMaintenance;
    }

    public ParkingSpot parkVehicle(Vehicle vehicle)
    {
        this.setVehicle(vehicle);
        return this;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }

    public ParkingFloor getParkingFloor() {
        return parkingFloor;
    }

    public void setParkingFloor(ParkingFloor parkingFloor) {
        this.parkingFloor = parkingFloor;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
