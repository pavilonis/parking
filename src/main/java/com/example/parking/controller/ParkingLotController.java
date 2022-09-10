package com.example.parking.controller;

import com.example.parking.domain.ParkingLot;
import com.example.parking.repository.ParkingLotCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("api/parking-lots")
@RequiredArgsConstructor
@RestController
public class ParkingLotController {

   private final ParkingLotCrudRepository repository;

   @GetMapping
   public List<ParkingLot> loadAll() {
      return repository.findAll();
   }
}
