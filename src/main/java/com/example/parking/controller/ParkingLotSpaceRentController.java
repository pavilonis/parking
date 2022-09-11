package com.example.parking.controller;

import com.example.parking.service.ParkingLotSpaceRentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// todo change to single parking lot (many companies, single lot)
@RequestMapping("api/booking")
@RequiredArgsConstructor
@RestController
public class ParkingLotSpaceRentController {

   private final ParkingLotSpaceRentService bookingService;

   @PostMapping
   public void add(int parkingLotId, int companyId, int spaces) {
      bookingService.bookSpaces(parkingLotId, companyId, spaces);
   }

   @PutMapping
   public void update(int parkingLotId, int companyId, int spaces) {
      bookingService.update(parkingLotId, companyId, spaces);
   }

   @DeleteMapping
   public void revoke(int parkingLotId, int companyId, int spaces) {
      bookingService.revoke(parkingLotId, companyId, spaces);
   }
}
