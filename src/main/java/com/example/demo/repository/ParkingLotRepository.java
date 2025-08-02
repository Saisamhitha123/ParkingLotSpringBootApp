package com.example.demo.repository;

import com.example.demo.model.entity.ParkingLot;
import com.example.demo.util.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingLotRepository extends JpaRepository<ParkingLot,Long> {



}
