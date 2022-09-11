package com.example.parking.repository;

import com.example.parking.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface ParkingLotCrudRepository extends JpaRepository<ParkingLot, Integer> {
   default ParkingLot getParkingLot(int parkingLotId) {
      return findById(parkingLotId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Parking lot not found"));
   }
}
