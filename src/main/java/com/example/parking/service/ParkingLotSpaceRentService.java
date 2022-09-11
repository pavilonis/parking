package com.example.parking.service;

import com.example.parking.domain.Company;
import com.example.parking.domain.ParkingLot;
import com.example.parking.repository.CompanyCrudRepository;
import com.example.parking.repository.ParkingLotCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@Service
public class ParkingLotSpaceRentService {

   private final CompanyCrudRepository companyRepository;
   private final ParkingLotCrudRepository parkingLotRepository;

   public void bookSpaces(int parkingLotId, int companyId, int spaces) {

      ParkingLot parkingLot = parkingLotRepository.findById(parkingLotId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking lot not found"));

      validateAvailability(parkingLot, spaces);

      Company company = companyRepository.findById(companyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking lot not found"));

      parkingLot.getCompanySpaceReservations()
            .merge(company, spaces, Integer::sum);
   }

   private void validateAvailability(ParkingLot parkingLot, int spacesRequested) {
      int reservationsSum = parkingLot.getCompanySpaceReservations()
            .values()
            .stream()
            .reduce(0, Integer::sum);

      int spacesAvailable = parkingLot.getSize() - reservationsSum;

      if (spacesAvailable < spacesRequested) {
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Requested more spaces than available");
      }
   }

}
