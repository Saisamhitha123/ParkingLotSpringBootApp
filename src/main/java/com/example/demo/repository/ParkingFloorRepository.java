package com.example.demo.repository;

import com.example.demo.model.entity.ParkingFloor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingFloorRepository extends JpaRepository<ParkingFloor,Long> {
}
