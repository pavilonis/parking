package com.example.parking.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyJoinColumn;
import java.util.Map;

@Setter
@Getter
@Entity
public class Company {

   @Id
   private int id;
   private String name;

   // It would make more sense for ParkingLot to hold map of companies renting spaces in it,
   // but it requires less code store map of rented parking spaces for company because of monitoring requirements
   @ElementCollection
   @CollectionTable(name = "PARKING_LOT_COMPANY_SPACES", joinColumns = @JoinColumn(name = "parking_lot_id"))
   @MapKeyJoinColumn(name = "company_id")
   @Column(name = "spaces")
   private Map<ParkingLot, Integer> parkingLotSpaces;

}
