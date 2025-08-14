package com.example.demo.model.entity;


import com.example.demo.service.FeeCalculationService.FeeCalculator;
import com.example.demo.util.AllocationStrategyType;
import com.example.demo.util.FeeCalculationType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
public class ExitWayPanel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long exitPanelId;
    private String name;
    @ManyToOne
    @JsonIgnore
    private ParkingLot parkingLot;

    @OneToOne(mappedBy = "exitWayPanel",cascade = CascadeType.PERSIST)
    private DisplayPanel displayPanel;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "Fee Type is Required")
    private FeeCalculationType feeType;


    public ExitWayPanel() {

    }

    public long getExitPanelId() {
        return exitPanelId;
    }

    public void setExitPanelId(long exitPanelId) {
        this.exitPanelId = exitPanelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public void setParkingLot(ParkingLot parkingLot) {
        this.parkingLot = parkingLot;
    }

    public DisplayPanel getDisplayPanel() {
        return displayPanel;
    }

    public void setDisplayPanel(DisplayPanel displayPanel) {
        this.displayPanel = displayPanel;
    }

    public FeeCalculationType getFeeType() {
        return feeType;
    }

    public void setFeeType(FeeCalculationType feeType) {
        this.feeType = feeType;
    }
}
