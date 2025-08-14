package com.example.demo.model.dto;

import com.example.demo.util.SpotType;
import com.example.demo.util.Status;

public class ParkingSpotDTO {

    private SpotType spotType;
    private VehicleDTO vehicle;
    private boolean isUnderMaintenance;
    private Status status;
    private Long spotId;

    // ✅ Private constructor for builder
    private ParkingSpotDTO(Builder builder) {
        this.spotType = builder.spotType;
        this.vehicle = builder.vehicle;
        this.isUnderMaintenance = builder.isUnderMaintenance;
        this.status = builder.status;
        this.spotId = builder.spotId;
    }

    // ✅ Builder class
    public static class Builder {
        private SpotType spotType;
        private VehicleDTO vehicle;
        private boolean isUnderMaintenance;
        private Status status;
        private Long spotId;

        public Builder setSpotType(SpotType spotType) {
            this.spotType = spotType;
            return this;
        }

        public Builder setVehicle(VehicleDTO vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder setUnderMaintenance(boolean isUnderMaintenance) {
            this.isUnderMaintenance = isUnderMaintenance;
            return this;
        }

        public Builder setStatus(Status status) {
            this.status = status;
            return this;
        }

        public Builder setSpotId(Long spotId) {
            this.spotId = spotId;
            return this;
        }

        public ParkingSpotDTO build() {
            return new ParkingSpotDTO(this);
        }
    }

    // Existing methods

    public SpotType getSpotType() {
        return spotType;
    }

    public void setSpotType(SpotType spotType) {
        this.spotType = spotType;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setUnderMaintenance(boolean underMaintenance) {
        isUnderMaintenance = underMaintenance;
    }

    public ParkingSpotDTO parkVehicle(VehicleDTO vehicle) {
        this.setVehicle(vehicle);
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getSpotId() {
        return spotId;
    }

    public void setSpotId(Long spotId) {
        this.spotId = spotId;
    }
}
