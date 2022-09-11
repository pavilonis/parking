package com.example.parking.service;

import com.example.parking.domain.ParkingEventType;
import com.example.parking.repository.GatesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class GatesService {

   private final GatesRepository repository;

   public void openGate(int parkingLotId, String numberPlate, ParkingEventType eventType) {
      validateGateRequest(parkingLotId, numberPlate);
      repository.saveEvent(parkingLotId, numberPlate, eventType);
   }

   public void validateGateRequest(int parkingLotId, String numberPlate) {
      if (repository.isNumberPlateValid(parkingLotId, numberPlate)) {
         log.info("Number plate {} is valid", numberPlate);
         return;
      }
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Number plate is not allowed in this parking lot");
   }

   public Map<String, Integer> loadNumberPlatesUsages() {
     return repository.loadNumberPlateUsages();
   }
}
