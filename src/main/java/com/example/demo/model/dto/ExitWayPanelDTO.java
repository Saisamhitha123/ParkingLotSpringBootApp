package com.example.demo.model.dto;

import com.example.demo.model.entity.DisplayPanel;
import com.example.demo.service.DisplayPanel.DisplayPanelService;
import com.example.demo.service.FeeCalculationService.FeeCalculator;

public class ExitWayPanelDTO {

    private DisplayPanelService displayPanel;
    private FeeCalculator feeCalculator;


    public ExitWayPanelDTO(DisplayPanel displayPanel) {
    }
}
