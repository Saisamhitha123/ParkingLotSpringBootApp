package com.example.demo.service.FeeCalculationService.impl;

import com.example.demo.model.entity.ParkingTicket;
import com.example.demo.model.entity.Vehicle;
import com.example.demo.service.FeeCalculationService.FeeCalculator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service("baseFeeCalculator")
public class BaseFeeCalculator implements FeeCalculator {
    public BigDecimal calculateFee(Vehicle vehicle) {
        
        ParkingTicket parkingTicket=vehicle.getParkingTicket();

        long durationInMillis = System.currentTimeMillis() - parkingTicket.getEntryTime();
        double durationInHours = durationInMillis / (1000.0 * 60 * 60);
        long hours= Math.round(durationInHours);
        BigDecimal bigDecimal=new BigDecimal(hours*1);
        return bigDecimal; // Base fee
    }

}
