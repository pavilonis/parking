package com.example.parking.controller;

import com.example.parking.domain.ParkingEventType;
import com.example.parking.domain.ParkingLot;
import com.example.parking.repository.ParkingLotCrudRepository;
import com.example.parking.service.GatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("api/parking-lots")
@RequiredArgsConstructor
@RestController
public class ParkingLotController {

   private final ParkingLotCrudRepository repository;
   private final GatesService gatesService;

   @GetMapping
   public List<ParkingLot> getAll() {
      return repository.findAll();
   }

   @GetMapping("/{parking-lot-id}/enter")
   public void enter(@PathVariable("parking-lot-id") int parkingLotId, @RequestParam("number-plate") String numberPlate) {
      log.info("Validating entrance of {} to parking lot {}", numberPlate, parkingLotId);
      gatesService.openGate(parkingLotId, numberPlate, ParkingEventType.ENTRANCE);
   }

   @GetMapping("/{parking-lot-id}/exit")
   public void exit(@PathVariable("parking-lot-id") int parkingLotId, @RequestParam("number-plate") String numberPlate) {
      log.info("Validating exit of {} from parking lot {}", numberPlate, parkingLotId);
      gatesService.openGate(parkingLotId, numberPlate, ParkingEventType.EXIT);
   }
}
