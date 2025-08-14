package com.example.demo.service.ParkingLotService.impl;

import com.example.demo.exception.ParkingLotCreationException;
import com.example.demo.model.dto.*;
import com.example.demo.model.entity.*;
import com.example.demo.repository.ParkingLotRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.service.AllocationStrategyService.AllocationStrategyService;
import com.example.demo.service.DisplayPanel.DisplayPanelService;
import com.example.demo.service.FeeCalculationService.FeeCalculator;
import com.example.demo.service.ParkingLotService.ParkingLotService;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingLotServiceImplementation implements ParkingLotService {

    @Autowired
    ParkingLotRepository parkingLotRepository;

    public ParkingLot addParkingLot(ParkingLot parkingLot) throws ParkingLotCreationException {

        return  parkingLotRepository.save(parkingLot);


    }
}
