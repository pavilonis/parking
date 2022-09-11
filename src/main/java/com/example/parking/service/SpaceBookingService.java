package com.example.parking.service;

import com.example.parking.domain.Company;
import com.example.parking.domain.ParkingLot;
import com.example.parking.repository.CompanyCrudRepository;
import com.example.parking.repository.ParkingLotCrudRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class SpaceBookingService {

   private final CompanyCrudRepository companyRepository;
   private final ParkingLotCrudRepository parkingLotRepository;

   @Transactional
   public void bookSpaces(int parkingLotId, int companyId, int spaces) {
      ParkingLot parkingLot = parkingLotRepository.getParkingLot(parkingLotId);
      Company company = companyRepository.getCompany(companyId);
      Map<Company, Integer> existingReservations = parkingLot.getCompanySpaceReservations();

      if (existingReservations.containsKey(company)) {
         throw new ResponseStatusException(HttpStatus.CONFLICT,
               "Can not rent NEW parking spaces - please update or revoke existing company rented spaces");
      }

      existingReservations.put(company, spaces);
      validateCapacity(parkingLot);
   }

   @Transactional
   public void update(int parkingLotId, int companyId, int spaces) {
      ParkingLot parkingLot = parkingLotRepository.getParkingLot(parkingLotId);
      Company company = companyRepository.getCompany(companyId);
      Map<Company, Integer> existingReservations = parkingLot.getCompanySpaceReservations();

      if (!existingReservations.containsKey(company)) {
         throw new ResponseStatusException(HttpStatus.CONFLICT,
               "Not possible to update - company has no rented spaces in parking lot " + parkingLot.getName());
      }

      existingReservations.put(company, spaces);
      validateCapacity(parkingLot);
   }

   @Transactional
   public void revoke(int parkingLotId, int companyId, int spacesToRevoke) {
      ParkingLot parkingLot = parkingLotRepository.getParkingLot(parkingLotId);
      Company company = companyRepository.getCompany(companyId);

      int companyReservations = parkingLot.getCompanySpaceReservations()
            .getOrDefault(company, 0);

      if (companyReservations < spacesToRevoke) {
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Can not revoke " + spacesToRevoke +
               " spaces, company currently has " + companyReservations + " spaces in parking lot " + parkingLot.getId());
      }

      parkingLot.getCompanySpaceReservations()
            .merge(company, spacesToRevoke, (spacesExisting, spacesToSubtract) -> spacesExisting - spacesToSubtract);
   }

   private void validateCapacity(ParkingLot parkingLot) {
      Map<Company, Integer> reservations = parkingLot.getCompanySpaceReservations();
      int reservationsSum = reservations.values().stream()
            .reduce(0, Integer::sum);

      if (reservationsSum > parkingLot.getSize()) {
         throw new ResponseStatusException(HttpStatus.CONFLICT,
               "Requested " + (reservationsSum - parkingLot.getSize()) + " more spaces than available in parking lot");
      }
   }
}
