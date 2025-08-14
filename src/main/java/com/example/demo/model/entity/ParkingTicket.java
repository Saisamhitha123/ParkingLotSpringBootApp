package com.example.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.math.BigDecimal;

@Entity
public class ParkingTicket {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long ticketId;


 @OneToOne
 @JsonIgnore
    private Vehicle vehicle;
    private  long entryTime;
    private BigDecimal amountCharged;

    public ParkingTicket(Long ticketId) {
        this.ticketId = ticketId;

        this.entryTime = System.currentTimeMillis();
    }

    public ParkingTicket(long entryTime) {
        this.entryTime = entryTime;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public BigDecimal getAmountCharged() {
        return amountCharged;
    }

    public void setAmountCharged(BigDecimal amountCharged) {
        this.amountCharged = amountCharged;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public ParkingTicket() {
    }
}
