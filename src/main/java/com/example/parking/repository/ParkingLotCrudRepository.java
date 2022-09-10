package com.example.parking.repository;

import com.example.parking.domain.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotCrudRepository extends JpaRepository<ParkingLot, Integer> {
}
