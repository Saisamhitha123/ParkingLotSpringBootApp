package com.example.demo.service.FeeCalculationService;

import org.springframework.stereotype.Service;

@Service
public abstract class FeeDecorator implements FeeCalculator{
    protected FeeCalculator  feeCalculator;
    public FeeDecorator(FeeCalculator feeCalculator) {
        this.feeCalculator = feeCalculator;
    }
}
