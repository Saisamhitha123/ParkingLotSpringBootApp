package com.example.demo.service.FeeCalculationService.impl;

import com.example.demo.model.entity.Vehicle;
import com.example.demo.service.FeeCalculationService.FeeCalculator;
import com.example.demo.service.FeeCalculationService.FeeDecorator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service("mediumSpotFee")
public class MediumSpotFee extends FeeDecorator {
    public MediumSpotFee(@Qualifier(value = "baseFeeCalculator")FeeCalculator feeCalculator) {
        super(feeCalculator);
    }

    public BigDecimal calculateFee(Vehicle vehicle) {
        return feeCalculator.calculateFee(vehicle).multiply(BigDecimal.valueOf(1.5));
    }

}
