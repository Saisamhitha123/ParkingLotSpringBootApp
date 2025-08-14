package com.example.demo.model.dto;

import com.example.demo.util.VehicleType;

import java.math.BigDecimal;


public class VehicleDTO {


    private String vehicleNumber;
    private VehicleType vehicleType;
  private ParkingTicketDTO parkingTicketDTO;

    public VehicleDTO(String vehicleNumber, VehicleType vehicleType,ParkingTicketDTO parkingTicketDTO) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.parkingTicketDTO=parkingTicketDTO;

    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public ParkingTicketDTO getParkingTicketDTO() {
        return parkingTicketDTO;
    }

    public void setParkingTicketDTO(ParkingTicketDTO parkingTicketDTO) {
        this.parkingTicketDTO = parkingTicketDTO;
    }
}
