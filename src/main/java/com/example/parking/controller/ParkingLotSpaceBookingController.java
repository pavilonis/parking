package com.example.parking.controller;

import com.example.parking.service.ParkingLotSpaceBookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("api/booking")
@RequiredArgsConstructor
@RestController
public class ParkingLotSpaceBookingController {

   private final ParkingLotSpaceBookingService bookingService;

   @PostMapping
   public void add(@RequestParam("parking-lot-id") int parkingLotId,
                   @RequestParam("company-id") int companyId,
                   @RequestParam int spaces) {

      log.info("Adding {} spaces to lot {}, company {}", spaces, parkingLotId, companyId);
      bookingService.bookSpaces(parkingLotId, companyId, spaces);
   }

   @PutMapping
   public void update(@RequestParam("parking-lot-id") int parkingLotId,
                      @RequestParam("company-id") int companyId,
                      @RequestParam int spaces) {
      log.info("Updating to {} spaces in lot {} for company {}", spaces, parkingLotId, companyId);
      bookingService.update(parkingLotId, companyId, spaces);
   }

   @DeleteMapping
   public void revoke(@RequestParam("parking-lot-id") int parkingLotId,
                      @RequestParam("company-id") int companyId,
                      @RequestParam int spaces) {
      log.info("Revoking {} spaces from lot {}, company {}", spaces, parkingLotId, companyId);
      bookingService.revoke(parkingLotId, companyId, spaces);
   }
}
