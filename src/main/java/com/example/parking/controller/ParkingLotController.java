package com.example.parking.controller;

import com.example.parking.service.GatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/parking-lots")
@RequiredArgsConstructor
@RestController
public class ParkingLotController {

   private final GatesService gatesService;

   @GetMapping("/{parking-lot-id}/enter")
   public void add(@PathVariable("parking-lot-id") int parkingLotId, @RequestParam("number-plate") String numberPlate) {
      log.info("Validating entrance of {} to parking lot {}", numberPlate, parkingLotId);
      gatesService.validateGateRequest(parkingLotId, numberPlate);
   }

   @GetMapping("/{parking-lot-id}/exit")
   public void remove(@PathVariable("parking-lot-id") int parkingLotId, @RequestParam("number-plate") String numberPlate) {
      log.info("Validating exit of {} from parking lot {}", numberPlate, parkingLotId);
      gatesService.validateGateRequest(parkingLotId, numberPlate);
   }
}
