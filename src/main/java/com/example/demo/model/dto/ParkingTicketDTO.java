package com.example.demo.model.dto;

import java.math.BigDecimal;

public class ParkingTicketDTO {

    private final String ticketId;
    private final long entryTime;
    private VehicleDTO vehicle;
    private ParkingSpotDTO spot;
    private BigDecimal amountCharged;

    // ✅ Private constructor for builder
    private ParkingTicketDTO(Builder builder) {
        this.ticketId = builder.ticketId;
        this.vehicle = builder.vehicle;
        this.spot = builder.spot;
        this.amountCharged = builder.amountCharged;
        this.entryTime = System.currentTimeMillis(); // Always set at creation
    }

    // ✅ Static Builder class
    public static class Builder {
        private String ticketId;
        private VehicleDTO vehicle;
        private ParkingSpotDTO spot;
        private BigDecimal amountCharged;

        public Builder setTicketId(String ticketId) {
            this.ticketId = ticketId;
            return this;
        }

        public Builder setVehicle(VehicleDTO vehicle) {
            this.vehicle = vehicle;
            return this;
        }

        public Builder setSpot(ParkingSpotDTO spot) {
            this.spot = spot;
            return this;
        }

        public Builder setAmountCharged(BigDecimal amountCharged) {
            this.amountCharged = amountCharged;
            return this;
        }

        public ParkingTicketDTO build() {
            return new ParkingTicketDTO(this);
        }
    }

    // ✅ Getters and Setters

    public String getTicketId() {
        return ticketId;
    }

    public VehicleDTO getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingSpotDTO getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpotDTO spot) {
        this.spot = spot;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public BigDecimal getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(BigDecimal amountCharged) {
        this.amountCharged = amountCharged;
    }
}
