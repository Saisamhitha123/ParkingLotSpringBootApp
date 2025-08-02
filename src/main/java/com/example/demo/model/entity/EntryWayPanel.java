package com.example.demo.model.entity;

import com.example.demo.service.AllocationStrategyService.AllocationStrategyService;
import com.example.demo.util.AllocationStrategyType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@Entity
public class EntryWayPanel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long entryPanelId;
    private String name;
    @ManyToOne
    @JsonIgnore
    private ParkingLot parkingLot;


    @Enumerated(EnumType.STRING)
    @NotNull(message = "Strategy Type is Required")
    private AllocationStrategyType strategyType;

    @OneToOne(mappedBy = "entryWayPanel",cascade = CascadeType.PERSIST)
    private DisplayPanel displayPanel;
    public EntryWayPanel() {

    }

    public AllocationStrategyType getStrategyType() {
        return strategyType;
    }

    public void setStrategyType(AllocationStrategyType strategyType) {
        this.strategyType = strategyType;
    }

    public long getEntryPanelId() {
        return entryPanelId;
    }

    public void setEntryPanelId(long entryPanelId) {
        this.entryPanelId = entryPanelId;
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
}
