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
public class ParkingLotSpaceRentService {

   private final CompanyCrudRepository companyRepository;
   private final ParkingLotCrudRepository parkingLotRepository;

   @Transactional
   public void bookSpaces(int parkingLotId, int companyId, int spaces) {
      ParkingLot parkingLot = getParkingLot(parkingLotId);
      Company company = getCompany(companyId);
      Map<Company, Integer> existingReservations = parkingLot.getCompanySpaceReservations();

      if (existingReservations.containsKey(company)) {
         throw new ResponseStatusException(HttpStatus.CONFLICT,
               "Can not rent NEW parking spaces - please update or revoke existing company rented spaces");
      }

      existingReservations.put(company, spaces);
      validateCapacity(parkingLot);
   }

   public void update(int parkingLotId, int companyId, int spaces) {
      ParkingLot parkingLot = getParkingLot(parkingLotId);
      Company company = getCompany(companyId);
      Map<Company, Integer> existingReservations = parkingLot.getCompanySpaceReservations();

      if (!existingReservations.containsKey(company)) {
         throw new ResponseStatusException(HttpStatus.CONFLICT,
               "Not possible to update - company has no rented spaces in parking lot " + parkingLot.getName());
      }

      existingReservations.put(company, spaces);
      validateCapacity(parkingLot);
   }

   public void revoke(int parkingLotId, int companyId, int spacesToRevoke) {
      ParkingLot parkingLot = getParkingLot(parkingLotId);
      Company company = getCompany(companyId);

      int companyReservations = parkingLot.getCompanySpaceReservations()
            .getOrDefault(company, 0);

      if (companyReservations < spacesToRevoke) {
         throw new ResponseStatusException(HttpStatus.CONFLICT, "Can not revoke " + spacesToRevoke +
               " spaces, company currently rents " + companyReservations + " spaces in parking lot " + parkingLot.getName());
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

   private ParkingLot getParkingLot(int parkingLotId) {
      return parkingLotRepository.findById(parkingLotId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking lot not found"));
   }

   private Company getCompany(int companyId) {
      return companyRepository.findById(companyId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company lot not found"));
   }
}
