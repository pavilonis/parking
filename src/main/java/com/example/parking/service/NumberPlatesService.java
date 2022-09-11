package com.example.parking.service;

import com.example.parking.repository.NumberPlateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class NumberPlatesService {

   private final NumberPlateRepository repository;

   public void addPlateNumbers(int parkingLotId, int companyId, String numberPlate) {
      try {
         repository.addPlateNumbers(parkingLotId, companyId, numberPlate);

      } catch (DuplicateKeyException e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
               "Number plate already exists for this company / parking lot");

      } catch (DataIntegrityViolationException e) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown company / parking lot ID received");
      }
   }

   public void removePlateNumbers(int parkingLotId, int companyId, String numberPlate) {
      int numbersOfRecordsDeleted = repository.removePlateNumbers(parkingLotId, companyId, numberPlate);
      if (numbersOfRecordsDeleted == 0) {
         throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number plate not found in company parking lot");
      }
      if (numbersOfRecordsDeleted > 1) {
         throw new IllegalStateException();
      }
   }
}
