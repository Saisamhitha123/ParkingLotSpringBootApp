package com.example.demo.service.AllocationStrategyService.impl;

import com.example.demo.controller.VehicleController;
import com.example.demo.exception.ParkingLotNotFoundException;
import com.example.demo.model.dto.ParkingFloorDTO;
import com.example.demo.model.dto.ParkingSpotDTO;
import com.example.demo.model.dto.VehicleDTO;
import com.example.demo.model.entity.*;
import com.example.demo.repository.ParkingFloorRepository;
import com.example.demo.repository.ParkingLotRepository;
import com.example.demo.repository.ParkingSpotRepository;
import com.example.demo.service.AllocationStrategyService.AllocationStrategyService;
import com.example.demo.util.SpotType;
import com.example.demo.util.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("PERCENTAGE")
public class PercentageAllocationStrategy implements AllocationStrategyService {

    private static final Logger logger = LoggerFactory.getLogger(PercentageAllocationStrategy.class);


    private final Map<SpotType,Double> spotTypePercentage=Map.of(SpotType.SMALL,0.3,SpotType.MEDIUM,0.5,SpotType.LARGE,0.2);

    @Autowired
    ParkingLotRepository parkingLotRepository;
    @Autowired
    ParkingFloorRepository parkingFloorRepository;
    @Autowired
    ParkingSpotRepository parkingSpotRepository;

    @Override
    public ParkingSpot vacantSpot(Long parkingLotId, Vehicle vehicle) {

        ParkingSpot allocated=null;
        Optional<ParkingLot> parkingLot=parkingLotRepository.findById(parkingLotId);


       /* Map<Status, List<ParkingFloorDTO>> floorsByStatus =
                parkingLotRepository.findById(parkingLotId).get().getParkingFloors().stream()
                        .map(floor -> new ParkingFloorDTO(
                                floor.getParkingSpots().stream().map(spot->new ParkingSpotDTO(spot.getSpotType(),spot.getVehicle()!=null?new VehicleDTO(spot.getVehicle().getVehicleNumber(),spot.getVehicle().getVehicleType(), null):null,spot.isUnderMaintenance(), spot.getStatus(),spot.getSpotId())).collect(Collectors.groupingBy(ParkingSpotDTO::getStatus,Collectors.groupingBy(ParkingSpotDTO::getSpotType))),
                                floor.getFloorId().toString(),
                                floor.getDisplayPanel(),floor.getStatus(),floor.isUnderMaintenance()))
                        .collect(Collectors.groupingBy(ParkingFloorDTO::getStatus));
*/

        Map<Status, List<ParkingFloorDTO>> floorsByStatus = parkingLotRepository.findById(parkingLotId).get().getParkingFloors().stream()
                .map(floor -> {
                    Map<Status, Map<SpotType, List<ParkingSpotDTO>>> groupedSpots = floor.getParkingSpots().stream()

                            .map(spot -> new ParkingSpotDTO.Builder()
                                    .setSpotType(spot.getSpotType())
                                    .setVehicle(spot.getVehicle() != null
                                            ? new VehicleDTO(spot.getVehicle().getVehicleNumber(), spot.getVehicle().getVehicleType(), null)
                                            : null)
                                    .setUnderMaintenance(spot.isUnderMaintenance())
                                    .setStatus(spot.getStatus())
                                    .setSpotId(spot.getSpotId())
                                    .build())
                            .collect(Collectors.groupingBy(
                                    ParkingSpotDTO::getStatus,
                                    Collectors.groupingBy(ParkingSpotDTO::getSpotType)
                            ));

                    // Build ParkingFloorDTO
                    return new ParkingFloorDTO.ParkingFloorDTOBuilder()
                            .setParkingSpots(groupedSpots)
                            .setFloorNumber(floor.getFloorId().toString())
                            .setDisplayPanel(floor.getDisplayPanel())
                            .setStatus(floor.getStatus())
                            .setIsUnderMaintenance(floor.isUnderMaintenance())
                            .build();
                })
                .collect(Collectors.groupingBy(ParkingFloorDTO::getStatus));


        System.out.println("coll"+floorsByStatus);
        SpotType requiredType=getSpotType(vehicle);
        System.out.println("re"+requiredType);
        List<ParkingFloorDTO> parkingFloor=floorsByStatus.get(Status.VACANT);
        System.out.println("chec"+parkingFloor.size());
        for(ParkingFloorDTO parkingFloorDTO:parkingFloor) {
            System.out.println("check");
            long totalSize = parkingFloorDTO.getParkingSpots().values().stream()
                    .flatMap(innerMap -> innerMap.values().stream())
                    .mapToInt(List::size)
                    .sum();
            long allowedSpots = Math.round(totalSize * spotTypePercentage.get(requiredType));
            System.out.println("all"+allowedSpots);

          /*  Map<Status,Map<SpotType,List<ParkingSpotDTO>>> parkingSpots =parkingFloorDTO.getParkingSpots();

            Map<SpotType,List<ParkingSpotDTO>>  typeListMap=parkingSpots.get(Status.VACANT);

           List<ParkingSpotDTO> parkingSpotDTO= typeListMap.get(getSpotType(vehicle));

         long occupiedSpots=  parkingSpotDTO.stream().filter(s->s.getStatus().equals("VACANT")).collect(Collectors.toList()).size();

*/
            long occupiedSpots = parkingFloorDTO.getParkingSpots().entrySet().stream()
                    .filter(entry -> entry.getKey() == Status.VACANT) // Keep only VACANT status
                    .flatMap(entry -> Optional.ofNullable(entry.getValue().get(getSpotType(vehicle)))
                            .stream()
                            .flatMap(List::stream))
                    .filter(spot -> "OCCUPIED".equals(spot.getStatus().toString()))
                    .count();

      /*      long occupiedSpots = parkingFloorDTO.getParkingSpots().values().stream()
                    .flatMap(innerMap -> innerMap.values().stream())
                    .flatMap(List::stream)
                    .filter(spot -> spot.getStatus().equals("OCCUPIED"))
                    .collect(Collectors.toList()).size();*/
            System.out.println("occupied"+occupiedSpots);
            if (allowedSpots > occupiedSpots) {
                System.out.println("inside");
                Optional<ParkingSpotDTO> parkingSpotDTO = parkingFloorDTO.getParkingSpots().entrySet().stream()
                        .filter(entry -> entry.getKey() == Status.VACANT) // Keep only VACANT status
                        .flatMap(entry -> Optional.ofNullable(entry.getValue().get(getSpotType(vehicle)))
                                .stream()
                                .flatMap(List::stream))
                        .filter(spot -> "VACANT".equals(spot.getStatus().toString())).findAny();

                System.out.println("parkingSpot0"+parkingSpotDTO.isPresent());
                System.out.println("parkingSpot0"+parkingSpotDTO);

                if (parkingSpotDTO.isPresent()) {

                    List<ParkingFloor> parkingFloors = parkingLot.get().getParkingFloors();

                    ParkingFloor parkingFloorTemp = parkingFloors.stream().filter(p -> p.getFloorId().equals(Long.valueOf(parkingFloorDTO.getFloorNumber()))).findFirst().get();
                    ParkingSpot spot = parkingFloorTemp.getParkingSpots().stream().filter(p -> p.getSpotId().equals(parkingSpotDTO.get().getSpotId())).findFirst().get();

                    vehicle.setParkingSpot(spot);
                    ParkingTicket parkingTicket = new ParkingTicket(System.currentTimeMillis());
                    vehicle.setParkingTicket(parkingTicket);
                    parkingTicket.setVehicle(vehicle);
                    spot.setVehicle(vehicle);
                    spot.setStatus(Status.OCCUPIED);

                    parkingFloorTemp.getParkingSpots().add(spot);
                    parkingLot.get().getParkingFloors().add(parkingFloorTemp);

                    parkingLotRepository.save(parkingLot.get());
                    allocated = spot;

                }
            }
        }
        System.out.println(allocated+"chec"+allocated.getSpotId());
return allocated;
    }

    private SpotType getSpotType(Vehicle vehicle)
    {
       return switch (vehicle.getVehicleType()){
           case TWO_WHEELER -> SpotType.SMALL;
           case CAR -> SpotType.MEDIUM;
           case BUS, TRUCk -> SpotType.LARGE;
       };
    }
}
