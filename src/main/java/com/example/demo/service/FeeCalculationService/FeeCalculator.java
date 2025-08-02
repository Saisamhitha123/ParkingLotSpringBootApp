package com.example.demo.service.FeeCalculationService;

import com.example.demo.model.entity.ParkingTicket;
import com.example.demo.model.entity.Vehicle;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface FeeCalculator {
    public BigDecimal calculateFee(Vehicle vehicle);
}
