package com.example.parking.controller;

import com.example.parking.service.NumberPlatesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/number-plates")
@RequiredArgsConstructor
@RestController
public class NumberPlatesController {
   private final NumberPlatesService service;

   @PostMapping("/{number-plate}")
   public void add(@RequestParam("parking-lot-id") int parkingLotId,
                   @RequestParam("company-id") int companyId,
                   @PathVariable("number-plate") String numberPlate) {

      log.info("Adding number plate {}", numberPlate);
      service.addPlateNumbers(parkingLotId, companyId, numberPlate);
      log.info("Number plate {} added", numberPlate);
   }

   @DeleteMapping("/{number-plate}")
   public void remove(@RequestParam("parking-lot-id") int parkingLotId,
                      @RequestParam("company-id") int companyId,
                      @PathVariable("number-plate") String numberPlate) {

      log.info("Removing number plate {}", numberPlate);
      service.removePlateNumbers(parkingLotId, companyId, numberPlate);
      log.info("Number plate {} removed", numberPlate);
   }
}
